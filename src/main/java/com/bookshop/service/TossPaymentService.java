package com.bookshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossPaymentService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${tossPayments.secretKey}")
    private String secretKey;

    public void verifyPayment(String paymentKey, String orderId, int amount) {
        String url = "https://api.tosspayments.com/v1/payments/confirm";

        // HTTP Header 설정 (Basic 인증 + JSON 타입)
        HttpHeaders headers = new HttpHeaders();
        String encodedAuth = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);

        //요청 바디 생성
        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", paymentKey);
        body.put("orderId", orderId);
        body.put("amount", amount);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            //toss 서버에 결제 확인 요청
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("Toss 결제 확인 실패 : 응답코드 = " + response.getStatusCode());
            }
            log.info("✅ Toss 결제 검증 성공: {} ", response.getBody());
        } catch (HttpClientErrorException e) {
            log.info("❌ Toss 결제 확인 실패 (HTTP 오류): {}", e.getResponseBodyAsString(), e);
            throw new IllegalStateException("결제 검증 실패: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.info("❌ Toss 결제 확인 중 예외 발생", e);
            throw new RuntimeException("Toss 결제 확인 중 오류: " + e.getMessage(), e);
        }
    }

}
