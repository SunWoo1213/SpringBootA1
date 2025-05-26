package com.shop.repository;

import com.shop.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserUsernameOrderByPurchaseTimeDesc(String username);
} 