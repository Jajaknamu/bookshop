package com.bookshop.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentVerificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    public JsonNode verifyPayment(String paymentId) {

        // 1. access_token 발급
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,String>> tokenReq = new HttpEntity<>(Map.of(
                "imp_key" ,apiKey,
                "imp_secret", apiSecret
        ), headers);
        ResponseEntity<String> tokenRes = restTemplate.postForEntity(
                "https://api.iamport.kt/users/getToken", tokenReq, String.class
        );
        String accessToken;
        try {
            JsonNode tokenBody = objectMapper.readTree(tokenRes.getBody());
            accessToken = tokenBody.get("response").get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("PortOne access token 획득 실패");
        }

        //2. 결제 조회
        HttpHeaders payHeaders = new HttpHeaders();
        payHeaders.setBearerAuth(accessToken);

        HttpEntity<Void> payReq = new HttpEntity<>(payHeaders);
        ResponseEntity<String> payRes = restTemplate.exchange(
                "https://api.iamport.kr/payments/" + paymentId,
                HttpMethod.GET,
                payReq,
                String.class
        );
        try {
            return objectMapper.readTree(payRes.getBody()).get("response");
        } catch (Exception e) {
            throw new RuntimeException("결제 조회 실패", e);
        }


    }
}
