package com.bookshop.service;

import com.bookshop.domain.Payment;
import com.bookshop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


//아임포트 API와 통신하여 결제를 검증하는 서비스
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    //결제 취소
    @Transactional
    public void updatePaymentToCanceled(String paymentKey) {
        Payment payment = paymentRepository.findByImpUid(paymentKey)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보가 없습니다.: " + paymentKey));

        payment.setStatus("canceled");
        payment.setCanceledAt(LocalDateTime.now());
    }

    //주문 ID(orderId)로 결제 정보 조회
    @Transactional(readOnly = true)
    public Payment findByOrderId(String orderId) {
        return paymentRepository.findByMerchantUid(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문의 결제 정보가 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Payment findByPaymentKey(String paymentKey) {
        return paymentRepository.findByImpUid(paymentKey)
                .orElseThrow(() -> new IllegalArgumentException("해당 paymentKey의 결제 정보가 없습니다."));
    }
}
