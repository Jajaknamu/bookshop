package com.bookshop.payment.repository;

import com.bookshop.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByImpUid(String impUid);
    Optional<Payment> findByMerchantUid(String uid);
}
