package com.bookshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import com.bookshop.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String adminDashboard(HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("loginMember");
        if (member == null || !"ADMIN".equals(member.getRole())) {
            return "redirect:/"; //권한없음
        }
        return "admin/adminPanel";
    }

    @GetMapping("/items")
    public String adminItemPage(HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("loginMember");
        if (member == null || !"ADMIN".equals(member.getRole())) {
            return "redirect:/";
        }
        return "admin/adminItems";
    }

    @GetMapping("/orders")
    public String adminOrderPage(HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("loginMember");
        if (member == null || !"ADMIN".equals(member.getRole())) {
            return "redirect:/";
        }
        return "admin/adminOrders";
    }

    //책 등록 폼
    @GetMapping("/items/new")
    public String showBookForm(HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("loginMember");
        if (member == null || "ADMIN".equals(member.getRole())) {
            return "redirect:/";
        }
        return "admin/adminBookForm";
    }
}
