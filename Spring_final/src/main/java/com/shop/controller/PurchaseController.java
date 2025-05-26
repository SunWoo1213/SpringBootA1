package com.shop.controller;

import com.shop.entity.Purchase;
import com.shop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/purchases")
    public String listPurchases(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Purchase> purchases = purchaseService.getUserPurchases(userDetails.getUsername());
        model.addAttribute("purchases", purchases);
        return "purchases";
    }
} 