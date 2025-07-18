package com.bookshop.service;

import com.bookshop.domain.Payment;
import com.bookshop.dto.PaymentVerificationResult;
import com.bookshop.repository.PaymentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//아임포트 API와 통신하여 결제를 검증하는 서비스
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }


    /*private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    private String accessToken;

//    @PostConstruct
    public void init() {
        this.accessToken = getAccessToken();
    }
    // 아임포트 서버로부터 access_token을 받아옴
    public String getAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";

        Map<String, String> body = new HashMap<>();
        body.put("imp_key", apiKey);
        body.put("imp_secret", apiSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        try {
            JsonNode json = objectMapper.readTree(response.getBody());
            return json.get("response").get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("아임포트 access_token 요청 실패", e);
        }
    }

    // imp_uid로 결제내역 조회 → 실제 결제 금액 반환
    public int getPaymentAmount(String impUid) {
        String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        try {
            JsonNode json = objectMapper.readTree(response.getBody());
            return json.get("response").get("amount").asInt();
        } catch (Exception e) {
            throw new RuntimeException("결제 조회 실패", e);
        }
    }*/

}
