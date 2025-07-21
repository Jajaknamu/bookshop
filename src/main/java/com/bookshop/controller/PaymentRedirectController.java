package com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/payments/view")
public class PaymentRedirectController {


    @GetMapping("/success")
    public String redirectSuccessPage() {
        log.info("✅ /payments/view/success 리다이렉트 컨트롤러 호출됨");
        // 결제 완료 후 보여줄 HTML 페이지로 이동 (Thymeleaf 사용)
        return "payment/success"; // → templates/payment/success.html 로 이동
    }

    @GetMapping("/fail")
    public String redirectFail() {
        log.warn("❌ 결제 실패 리다이렉트");
        return "redirect:/"; // 홈으로 보내든, 실패 페이지 만들든 자유롭게 설정
    }
}
