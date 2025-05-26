package com.shop.service;

import com.shop.entity.Purchase;
import com.shop.entity.User;
import com.shop.entity.Product;
import com.shop.repository.PurchaseRepository;
import com.shop.repository.UserRepository;
import com.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    public List<Purchase> getUserPurchases(String username) {
        return purchaseRepository.findByUserUsernameOrderByPurchaseTimeDesc(username);
    }

    @Transactional
    public void createPurchase(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
                
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setProduct(product);
        
        purchaseRepository.save(purchase);
    }
} 