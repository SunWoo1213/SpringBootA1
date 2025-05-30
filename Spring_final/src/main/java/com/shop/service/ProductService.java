package com.shop.service;

import com.shop.entity.Product;
import com.shop.entity.Category;
import com.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts(String sort) {
        Sort sortBy;
        try {
            sortBy = createSort(sort);
        } catch (Exception e) {
            sortBy = Sort.by(Sort.Direction.DESC, "id");
        }
        return productRepository.findAll(sortBy);
    }

    public List<Product> getProductsByCategory(Category category, String sort) {
        Sort sortBy;
        try {
            sortBy = createSort(sort);
        } catch (Exception e) {
            sortBy = Sort.by(Sort.Direction.DESC, "id");
        }
        return productRepository.findByCategory(category, sortBy);
    }

    public Map<Category, Product> getLatestProductsByCategory() {
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");
        return productRepository.findAll(sortByCreatedAt).stream()
                .collect(Collectors.groupingBy(
                    Product::getCategory,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.isEmpty() ? null : list.get(0)
                    )
                ));
    }

    private Sort createSort(String sort) {
        if (sort == null) {
            return Sort.by(Sort.Direction.DESC, "id");
        }
        return switch (sort.toLowerCase()) {
            case "latest" -> Sort.by(Sort.Direction.DESC, "createdAt");
            case "price_asc" -> Sort.by(Sort.Direction.ASC, "price");
            case "price_desc" -> Sort.by(Sort.Direction.DESC, "price");
            default -> Sort.by(Sort.Direction.DESC, "id");
        };
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + id));
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
} 