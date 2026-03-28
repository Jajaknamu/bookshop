package com.bookshop.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping
    public String adminDashboard(Authentication authentication) {
        if (!isAdmin(authentication)) {
            return "redirect:/";
        }
        return "admin/adminPanel";
    }

    @GetMapping("/items")
    public String adminItemPage(Authentication authentication) {
        if (!isAdmin(authentication)) {
            return "redirect:/";
        }
        return "admin/adminItems";
    }

    @GetMapping("/orders")
    public String adminOrderPage(Authentication authentication) {
        if (!isAdmin(authentication)) {
            return "redirect:/";
        }
        return "admin/adminOrders";
    }

    //책 등록 폼
    @GetMapping("/items/new")
    public String showBookForm(Authentication authentication) {
        if (!isAdmin(authentication)) {
            return "redirect:/";
        }
        return "admin/adminBookForm";
    }

    // JWT의 Authentication에서 ADMIN 권한 확인
    private boolean isAdmin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}