/*
package com.bookshop.service;

import com.bookshop.dto.PaymentCompleteRequest;
import com.bookshop.dto.PaymentVerificationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentVerificationService { //포트원 사용시 썼던 코드들

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    // Access Token 발급 메서드 분리 및 수정
    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // PortOne V2 방식에 맞는 body 구성
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("client_id", apiKey);
        requestBody.put("client_secret", apiSecret);

        String bodyJson;
        try {
            bodyJson = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            log.error("Access Token 요청 JSON 변환 실패", e);
            throw new RuntimeException("Access Token 요청 JSON 변환 실패", e);
        }

        System.out.println("💬 Access Token 요청 JSON = " + bodyJson); // 이 로그는 Access Token 발급 요청 바디입니다.

        HttpEntity<String> entity = new HttpEntity<>(bodyJson, headers);

        String tokenUrl = "https://api.portone.io/oauth/token"; // PortOne V2 Access Token 발급 URL
        System.out.println("💬 Access Token 요청 URL = " + tokenUrl);

        try {
            ResponseEntity<String> tokenRes = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            JsonNode tokenBody = objectMapper.readTree(tokenRes.getBody());
            // PortOne V2 응답 구조는 "response" 필드 없이 바로 "access_token"을 포함합니다.
            String accessToken = tokenBody.get("access_token").asText();
            System.out.println("💬 발급된 Access Token: " + accessToken);

            return accessToken;
        } catch (HttpClientErrorException e) {
            log.error("Access Token 발급 실패 (HTTP Error): {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("Access Token 발급 실패: " + e.getMessage() + ", Response: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.error("Access Token 발급 중 오류 발생", e);
            throw new RuntimeException("Access Token 발급 중 오류 발생: " + e.getMessage(), e);
        }
    }

    */
/**
     * 아임포트 서버에서 결제 상세정보 조회
     * @param paymentId 아임포트 결제 고유번호 (PortOne V2에서는 paymentId)
     * @return 결제 상세정보 JSON
     *//*

    public JsonNode verifyPayment(String paymentId) {

        String accessToken = getAccessToken(); // 분리된 메서드 호출

        // 2. 결제 조회 요청
        HttpHeaders payHeaders = new HttpHeaders();
        payHeaders.setBearerAuth(accessToken); // Bearer Token 설정

        HttpEntity<Void> payReq = new HttpEntity<>(payHeaders);

        // 🚨 이 부분이 핵심! PortOne V2 결제 조회 엔드포인트로 변경!
        String paymentInfoUrl = "https://api.portone.io/payments/" + paymentId;
        System.out.println("💬 결제 정보 조회 URL = " + paymentInfoUrl);
        System.out.println("💬 조회할 paymentId = " + paymentId);


        try {
            ResponseEntity<String> payRes = restTemplate.exchange(
                    paymentInfoUrl, // 수정된 URL 사용
                    HttpMethod.GET,
                    payReq,
                    String.class
            );

            // PortOne V2 결제 조회 응답 구조는 "response" 필드 없이 바로 결제 정보를 포함합니다.
            // V1에서는 .get("response")가 필요했지만, V2에서는 필요 없을 수 있습니다.
            // 따라서 응답 JSON 구조를 확인하고 파싱 방식을 결정해야 합니다.
            JsonNode payResponseBody = objectMapper.readTree(payRes.getBody());
            // PortOne V2 API 문서에 따르면, 성공 응답은 바로 결제 정보를 반환합니다.
            // {"status":"paid", "amount":1000, ...}
            // 따라서 "response" 없이 바로 반환합니다.
            return payResponseBody;

        } catch (HttpClientErrorException e) {
            log.error("아임포트 서버 통신 오류 (HTTP Error): {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("아임포트 서버 통신 오류: " + e.getStatusCode() + " " + e.getStatusText() + ": " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.error("결제 정보 조회 중 오류 발생", e);
            throw new RuntimeException("결제 정보 조회 중 오류 발생: " + e.getMessage(), e);
        }
    }

    */
/**
     * 아임포트로부터 결제 정보를 조회하고 상태 및 금액을 검증함
     * @param paymentId 아임포트 결제 고유 ID (PortOne V2에서는 paymentId)
     * @param expectedAmount 서버에서 계산한 기대 결제 금액
     * @return 검증된 결제 정보 DTO
     *//*

    public PaymentVerificationResult  validateAndVerifyPayment(String paymentId, int expectedAmount) {

        //1. 결제정보 조회
        JsonNode paymentInfo = verifyPayment(paymentId); // 이 부분에서 에러가 발생했다면 verifyPayment에서 이미 RuntimeException 던짐
        System.out.println("💬 조회된 결제 정보: " + paymentInfo.toPrettyString()); // 조회된 전체 정보 로그

        //2. 상태 검증
        String status = paymentInfo.get("status").asText();
        if (!"paid".equals(status)) {
            throw new IllegalStateException("결제 상태가 유효하지 않습니다.: " + status);
        }
        //3. 금액 검증
        int paidAmount = paymentInfo.get("amount").asInt();
        if (paidAmount != expectedAmount) {
            throw new IllegalStateException("결제 금액이 상품 가격과 일치하지 않습니다.");
        }

        //4. custom_data 파싱 (PortOne V2에서는 customData가 payment 객체 바로 아래에 올 수 있습니다)
        JsonNode customData = paymentInfo.get("customData"); // 필드 이름 확인: custom_data 또는 customData
        if (customData == null || customData.isNull()) { // custom_data가 null이거나 없으면 예외
            throw new IllegalStateException("customData가 존재하지 않거나 유효하지 않습니다.");
        }

        // customData가 Map 형태의 JSON으로 왔다고 가정
        Long memberId = null;
        Long itemId = null;
        int count = 0;

        if (customData.has("memberId")) {
            memberId = customData.get("memberId").asLong();
        } else {
            log.warn("customData에 memberId 필드가 없습니다.");
        }
        if (customData.has("itemId")) {
            itemId = customData.get("itemId").asLong();
        } else {
            log.warn("customData에 itemId 필드가 없습니다.");
        }
        if (customData.has("count")) {
            count = customData.get("count").asInt();
        } else {
            log.warn("customData에 count 필드가 없습니다.");
        }

        // memberId, itemId가 null일 경우의 처리 로직 추가 필요 (예: 기본값 설정 또는 예외 발생)
        if (memberId == null || itemId == null) {
            throw new IllegalStateException("customData에 필수 필드(memberId, itemId)가 누락되었습니다.");
        }

        return new PaymentVerificationResult(
                paymentId,
                paymentInfo.get("merchant_uid").asText(), // merchant_uid 필드 존재 여부 확인
                paidAmount,
                status,
                memberId,
                itemId,
                count
        );
    }

    //api 키들 잘 가져오는지 로그로 확인용
    @PostConstruct
    public void checkKeys() {
        System.out.println("⚠️ [IAMPORT KEY] = " + apiKey);
        System.out.println("⚠️ [IAMPORT SECRET] = " + apiSecret);
    }

*/
/*
    *//*
*/
/**
     * 아임포트 서버에서 결제 상세정보 조회
     * @param paymentId 아임포트 결제 고유번호 (imp_uid)
     * @return 결제 상세정보 JSON
     *//*
*/
/*
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

            String tokenUrl = "https://api.portone.io/oauth/token"; // <-- 이 부분을 수정하세요!
            System.out.println("💬 Access Token 요청 URL = " + tokenUrl); // 추가 로그

            ResponseEntity<String> tokenRes = restTemplate.postForEntity(
                    tokenUrl,
                    tokenReq,
                    String.class
            );

            System.out.println("💬 요청 JSON = " + bodyJson);

            JsonNode tokenBody = objectMapper.readTree(tokenRes.getBody());
            String accessToken = tokenBody.get("response").get("access_token").asText();

            // 2. 결제 조회 요청
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

    *//*
*/
/**
     * 아임포트로부터 결제 정보를 조회하고 상태 및 금액을 검증함
     * @param paymentId 아임포트 결제 고유 ID (imp_uid)
     * @param expectedAmount 서버에서 계산한 기대 결제 금액
     * @return 검증된 결제 정보 DTO
     *//*
*/
/*
    public PaymentVerificationResult  validateAndVerifyPayment(String paymentId, int expectedAmount) {

        //1. 결제정보 조회
        JsonNode paymentInfo = verifyPayment(paymentId);
        //2. 상태 검증
        String status = paymentInfo.get("status").asText();
        if (!"paid".equals(status)) {
            throw new IllegalStateException("결제 상태가 유효하지 않습니다.: " + status);
        }
        //3. 금액 검증
        int paidAmount = paymentInfo.get("amount").asInt();
        if (paidAmount != expectedAmount) {
            throw new IllegalStateException("결제 금액이 상품 가격과 일치하지 않습니다.");
        }

        //4. custom_data 파싱
        JsonNode customData = paymentInfo.get("custom_data");
        if (customData == null) {
            throw new IllegalStateException("custom_data가 존재하지 않습니다.");
        }

        Long memberId = customData.get("memberId").asLong();
        Long itemId = customData.get("itemId").asLong();
        int count = customData.get("count").asInt();

        return new PaymentVerificationResult(
                paymentId,
                paymentInfo.get("merchant_uid").asText(),
                paidAmount,
                status,
                memberId,
                itemId,
                count
        );
    }

    //api 키들 잘 가져오는지 로그로 확인용
    @PostConstruct
    public void checkKeys() {
        System.out.println("⚠️ [IAMPORT KEY] = " + apiKey);
        System.out.println("⚠️ [IAMPORT SECRET] = " + apiSecret);    }*//*


}
*/
