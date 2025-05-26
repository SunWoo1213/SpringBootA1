package com.shop.controller;

import com.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email,
                         @RequestParam String nickname,
                         RedirectAttributes redirectAttributes) {
        try {
            userService.register(username, password, email, nickname);
            redirectAttributes.addFlashAttribute("message", "회원가입을 완료했습니다!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
} 