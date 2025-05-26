package com.shop.controller;

import com.shop.entity.Product;
import com.shop.entity.Category;
import com.shop.service.ProductService;
import com.shop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final PurchaseService purchaseService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails,
                      @RequestParam(required = false, defaultValue = "latest") String sort) {
        try {
            Map<Category, Product> mostViewedProducts = productService.getMostViewedProductsByCategory();
            model.addAttribute("mostViewedProducts", mostViewedProducts);
            model.addAttribute("categories", Category.values());
            model.addAttribute("currentSort", sort);
            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }
            return "home";
        } catch (Exception e) {
            log.error("Error in home page: ", e);
            model.addAttribute("error", "상품 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }

    @GetMapping("/products")
    public String listProducts(Model model, @AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam(required = false, defaultValue = "latest") String sort) {
        try {
            List<Product> products = productService.getAllProducts(sort);
            model.addAttribute("products", products);
            model.addAttribute("categories", Category.values());
            model.addAttribute("currentSort", sort);
            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }
            return "products";
        } catch (Exception e) {
            log.error("Error in product list: ", e);
            model.addAttribute("error", "상품 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }

    @GetMapping("/products/category/{category}")
    public String listProductsByCategory(@PathVariable Category category, 
                                       @RequestParam(required = false, defaultValue = "latest") String sort,
                                       Model model, 
                                       @AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<Product> products = productService.getProductsByCategory(category, sort);
            model.addAttribute("products", products);
            model.addAttribute("categories", Category.values());
            model.addAttribute("currentCategory", category);
            model.addAttribute("currentSort", sort);
            if (userDetails != null) {
                model.addAttribute("username", userDetails.getUsername());
            }
            return "products";
        } catch (Exception e) {
            log.error("Error in category list: ", e);
            model.addAttribute("error", "카테고리별 상품 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }

    @GetMapping("/products/{id}")
    public String viewProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.incrementViewCount(id);
            return "redirect:/products";
        } catch (Exception e) {
            log.error("Error incrementing view count: ", e);
            redirectAttributes.addFlashAttribute("message", "조회수 업데이트 중 오류가 발생했습니다.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/products";
        }
    }

    @PostMapping("/products/{id}/purchase")
    public String purchaseProduct(@PathVariable Long id, 
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        try {
            purchaseService.createPurchase(userDetails.getUsername(), id);
            return "redirect:/purchases";
        } catch (Exception e) {
            log.error("Error in purchase: ", e);
            redirectAttributes.addFlashAttribute("message", "구매 처리 중 오류가 발생했습니다.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/products";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            if (product.getPrice() < 0) {
                throw new IllegalArgumentException("가격은 0보다 작을 수 없습니다.");
            }
            
            if (product.getName() == null || product.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("상품명은 필수입니다.");
            }

            if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
                throw new IllegalArgumentException("상품 설명은 필수입니다.");
            }

            if (product.getImageUrl() == null || product.getImageUrl().trim().isEmpty()) {
                product.setImageUrl("https://via.placeholder.com/300x200");
            }

            if (product.getCategory() == null) {
                throw new IllegalArgumentException("카테고리는 필수입니다.");
            }

            productService.saveProduct(product);
            redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 추가되었습니다.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            log.error("Error adding product: ", e);
            redirectAttributes.addFlashAttribute("message", "상품 추가 실패: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 삭제되었습니다.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            log.error("Error deleting product: ", e);
            redirectAttributes.addFlashAttribute("message", "상품 삭제 실패: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/products";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products/{id}/update")
    public String updateProduct(@PathVariable Long id, 
                              @ModelAttribute Product product, 
                              RedirectAttributes redirectAttributes) {
        try {
            product.setId(id);
            if (product.getImageUrl() == null || product.getImageUrl().trim().isEmpty()) {
                product.setImageUrl("https://via.placeholder.com/300x200");
            }
            if (product.getCategory() == null) {
                throw new IllegalArgumentException("카테고리는 필수입니다.");
            }
            productService.saveProduct(product);
            redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            log.error("Error updating product: ", e);
            redirectAttributes.addFlashAttribute("message", "상품 수정 실패: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/products";
    }
} 