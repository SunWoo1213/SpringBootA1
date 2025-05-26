package com.shop.service;

import com.shop.entity.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    
    public List<Purchase> getUserPurchases(String username) {
        // TODO: Implement actual purchase retrieval logic
        return List.of(); // 임시로 빈 리스트 반환
    }

    @Transactional
    public void createPurchase(String username, Long productId) {
        // TODO: Implement purchase creation logic
        // 1. Find user by username
        // 2. Find product by productId
        // 3. Create and save purchase
    }
} 