package com.bookshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.bookshop.domain.Member;
import com.bookshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    //로그인 화면
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm,
                        HttpServletRequest request,
                        Model model) {
        Member loginMember = memberService.findByName(loginForm.getName());
        if (loginMember == null) {
            model.addAttribute("loginError", "회원이 존재 하지 않음");
            return "bookList";
        }
        //세션 생성
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
