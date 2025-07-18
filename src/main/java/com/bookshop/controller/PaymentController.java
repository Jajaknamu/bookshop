/*
package com.bookshop.controller;

import com.bookshop.domain.item.Item;
import com.bookshop.dto.PaymentRequestDto;
import com.bookshop.repository.MemberRepository;
import com.bookshop.service.ItemService;
import com.bookshop.service.OrderService;
import com.bookshop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final ItemService itemService;

    @PostMapping("/verify")
    public String verifyPayment(@RequestBody PaymentRequestDto dto, Model model) {
        //1. 실제 결제 금액 조회
        int paidAmount = paymentService.getPaymentAmount(dto.getImp_uid());

        // 2. 서버에서 예상 금액 계산 (상품가격 * 수량)
        Item item = itemService.findOne(dto.getItemId());
        int expectedAmount = item.getPrice() * dto.getCount();

        // 3. 금액 비교
        if (paidAmount != expectedAmount) {
            throw new IllegalStateException("결제 금액이 일치하지 않습니다.");
        }

        // 4. 주문 생성
        Long orderId = orderService.order(dto.getMemberId(), dto.getItemId(), dto.getCount());

        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", paidAmount);

        return "payment/success"; //템플릿으로 forward

       */
/* return ResponseEntity.ok(Map.of(
                "result", "success",
                "orderId", orderId,
                "amount", paidAmount
        ));*//*

    }
}
*/
