package com.bookshop.controller;

import com.bookshop.domain.Payment;
import com.bookshop.dto.PaymentCompleteRequest;
import com.bookshop.dto.PaymentVerificationResult;
import com.bookshop.service.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentApiController {

//    private final PaymentVerificationService paymentVerificationService; // 아임포트 검증용
    private final TossPaymentService tossPaymentService;
    private final PaymentService paymentService; //결제 내역 저장용
    private final OrderService orderService; // 주문 생성용
    private final ItemService itemService;


    /**
     * ✅ Toss 결제 성공 콜백 처리
     * Toss에서 결제 성공 시 redirect 되는 주소에서 결제 검증 및 주문 생성 처리
     */
    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(@RequestParam String paymentKey,
                                            @RequestParam String orderId,
                                            @RequestParam int amount,
                                            @RequestParam Long memberId,
                                            @RequestParam Long itemId,
                                            @RequestParam int count) {
        log.info("✅ Toss 결제 성공 콜백 진입");

        try {
            log.info("✅ [결제 성공 콜백] paymentKey={}, orderId={}, amount={}", paymentKey, orderId, amount);

            // 1. Toss 서버에 결제 검증 요청
            tossPaymentService.verifyPayment(paymentKey, orderId, amount);
            log.info("✅ Toss 결제 검증 완료");

            // 2. 주문 생성
            Long orderIdResult = orderService.order(memberId, itemId, count);
            log.info("🛒 주문 생성 완료: orderId={}", orderIdResult);

            // 3. 결제 내역 저장
            Payment payment = new Payment();
            payment.setImpUid(paymentKey);  // Toss에서는 paymentKey를 unique ID처럼 사용
            payment.setMerchantUid(orderId);
            payment.setAmount(amount);
            payment.setStatus("paid");
            payment.setMemberId(memberId);
            payment.setItemId(itemId);
            payment.setCount(count);
            paymentService.savePayment(payment);

            // 4. 응답 반환
            return ResponseEntity.ok(Map.of(
                    "status", "OK",
                    "orderId", orderIdResult,
                    "message", "결제가 정상적으로 완료되었습니다."
            ));
        } catch (Exception e) {
            log.error("❌ 결제 처리 실패", e);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "FAIL",
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * ⛔️ 실패 시 콜백
     */
    @GetMapping("/fail")
    public ResponseEntity<?> paymentFail(@RequestParam Map<String, String> params) {
        log.warn("❌ 결제 실패 콜백: {}", params);
        return ResponseEntity.badRequest().body(Map.of(
                "status", "FAIL",
                "reason", params.getOrDefault("message", "결제가 실패했습니다.")
        ));
    }
}
