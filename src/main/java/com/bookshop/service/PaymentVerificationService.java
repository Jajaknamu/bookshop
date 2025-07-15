package com.bookshop.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
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

        try {
            // 바디를 JSON 문자열로 직접 변환
            Map<String, String> bodyMap = Map.of(
                    "imp_key", apiKey,
                    "imp_secret", apiSecret
            );
            String bodyJson = objectMapper.writeValueAsString(bodyMap);

            HttpEntity<String> tokenReq = new HttpEntity<>(bodyJson, headers);

            ResponseEntity<String> tokenRes = restTemplate.postForEntity(
                    "https://api.iamport.kr/users/getToken",
                    tokenReq,
                    String.class
            );

            System.out.println("💬 요청 JSON = " + bodyJson);

            JsonNode tokenBody = objectMapper.readTree(tokenRes.getBody());
            String accessToken = tokenBody.get("response").get("access_token").asText();

            // 2. 결제 조회
            HttpHeaders payHeaders = new HttpHeaders();
            payHeaders.setBearerAuth(accessToken);

            HttpEntity<Void> payReq = new HttpEntity<>(payHeaders);
            ResponseEntity<String> payRes = restTemplate.exchange(
                    "https://api.iamport.kr/payments/" + paymentId,
                    HttpMethod.GET,
                    payReq,
                    String.class
            );

            return objectMapper.readTree(payRes.getBody()).get("response");


        } catch (Exception e) {
            throw new RuntimeException("아임포트 서버 통신 오류: " + e.getMessage(), e);
        }

    }


    /*public JsonNode verifyPayment(String paymentId) {

        // 1. access_token 발급
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("imp_key", apiKey);
        requestBody.put("imp_secret", apiSecret);
        HttpEntity<Map<String, Object>> tokenReq = new HttpEntity<>(requestBody, headers);

       *//* HttpEntity<Map<String,String>> tokenReq = new HttpEntity<>(Map.of(
                "imp_key" ,apiKey,
                "imp_secret", apiSecret
        ), headers);*//*

        ResponseEntity<String> tokenRes = restTemplate.postForEntity(
                "https://api.iamport.kr/users/getToken", tokenReq, String.class
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
    }*/

    //api 키들 잘 가져오는지 로그로 확인용
    @PostConstruct
    public void checkKeys() {
        System.out.println("⚠️ [IAMPORT KEY] = " + apiKey);
        System.out.println("⚠️ [IAMPORT SECRET] = " + apiSecret);    }
}
