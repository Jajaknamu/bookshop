package com.bookshop.service;

import com.bookshop.domain.Payment;
import com.bookshop.dto.PaymentVerificationResult;
import com.bookshop.repository.PaymentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.yml")
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    void 결제정보_정상_저장(){

    }

}