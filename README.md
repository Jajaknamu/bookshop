# 📚 BookShop - 주문 관리 시스템
**Java / Spring Boot / Spring Data JPA / REST API 기반 사이드 프로젝트**   
**➕기능 확장: TossPayments 결제 시스템 추가**

> 📬 이메일: heyfer6867@gmail.com  
> 💼 포트폴리오: [자세히 보기 (Notion)](https://unique-income-725.notion.site/BookShop-1d2dee6a3251801caf76cca3b5dff517?source=copy_link)

## 💡 프로젝트 소개
> `실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발` 강의를 참고하여 만든 프로젝트입니다.  
> 이 프로젝트는 강의 예제를 기반으로 시작했지만 단순 구현을 넘어 상품-회원-주문 도메인 간 연관관계 설계, API 설계, 트랜잭션 흐름에 대한 이해를 확장하는 데 목적을 두었습니다.   

> 기존 예제인 Thymeleaf를 통한 **서버 렌더링** 구현을 넘어 **REST API**를 적용하여 데이터 조회와 관리 기능을 개선했습니다.   
> 주문 시스템에서 **Toss Payments**를 활용하여 실제 토스앱으로 연동해 결제가능 및 취소 로직을 구현했습니다.

## 👥 개발 기간
#### 2025.04.07 ~ 2025.04.27


## ⚙️ 개발 환경 및 기술 스택

| Category            | Stack                                                                 |
|-----------------    |-----------------------------------------------------------------------|
| **Language**        | Java 17                                                               |
| **Backend**         | Spring Boot 3.2.5                                                     |
| **Frontend**        | Thymeleaf, HTML/CSS, JavaScript, AJAX                                 |
| **Template Engine** | Thymeleaf                                                             |
| **Database / ORM**  | H2, Spring Data JPA                                                   |
| **Build Tool**      | Gradle                                                                |
| **IDE**             | IntelliJ IDEA                                                         |
| **Tools**           | Git, GitHub                                                           |
| **Testing**         |JUnit                                                                  |


## 🧩 핵심 기능
- 회원가입 / 로그인 / 주문 내역 확인
- 상품 등록, 수정, 삭제 (관리자 전용)
- 주문 생성 / 취소 + 재고 수량 반영
- REST API 기반 CRUD 구현
  
**➕기능 확장**
- **결제 요청/승인**: Toss SDK를 통한 결제창 호출 → 서버 API(/api/payments/success)에서 결제 검증 및 주문 생성
- **주문-결제 연동**: 결제 성공 시 OrderService.order() 호출로 주문 및 결제 내역 DB 저장
- **결제 검증**: Toss REST API를 통해 결제 금액/상태 검증 후 DB 반영
- **결제 취소**: 주문 취소 시 Toss 결제 취소 API 연동 → 실제 결제 취소 + DB 상태 업데이트

## 🧾 API 명세서

### 간단 요약

| Method   | URL                       | 요청 DTO            | 응답 DTO                   | 설명          |
| -------- | ------------------------- | ----------------- | ------------------------ | ----------- |
| `GET`    | `/api/orders`             | 없음                | `List<OrderResponseDto>` | 전체 주문 목록 조회 |
| `POST`   | `/api/orders`             | `OrderRequestDto` | `Long` (주문 ID)           | 주문 생성       |
| `PATCH`  | `/api/orders/{id}/cancel` | 없음                | `String`                 | 주문 취소       |
| `GET`    | `/api/orders/{id}`        | 없음                | `OrderResponseDto`       | 주문 상세 조회    |
|          |                           |                     |                          |                  |
| `GET`    | `/api/members`            | 없음                | `List<MemberDto>`        | 전체 회원 목록 조회 |
| `POST`   | `/api/members`            | `MemberDto`       | `Long` (회원 ID)           | 회원 등록       |
| `PATCH`  | `/api/members/{id}`       | `MemberUpdateDto` | `String`                 | 회원 정보 수정    |
| `DELETE` | `/api/members/{id}`       | 없음                | `String`                 | 회원 삭제       |
|          |                           |                     |                          |                 |
| `GET`    | `/api/items`              | 없음                | `List<BookDto>`          | 전체 상품 목록 조회 |
| `POST`   | `/api/items`              | `BookDto`         | `Long` (상품 ID)           | 상품 등록       |
| `GET`    | `/api/items/{id}`         | 없음                | `BookDto`                | 상품 상세 조회    |
| `PATCH`  | `/api/items/{id}`         | `BookDto`         | `String`                 | 상품 수정       |
| `DELETE` | `/api/items/{id}`         | 없음                | `String`                 | 상품 삭제       |

### ➕결제API
| Method | URL                     | 설명                 |
| ------ | ----------------------- | ------------------ |
| GET   | `/api/payments/success` | 토스 결제 성공 콜백 처리              |
| POST   | `/api/payments/cancel` | 결제 취소 |
| GET   | `/api/payments/fail`  | 토스 결제 실패 콜백 저장/응답          |
| GET   | `/payments/view/success` | 결제 성공 결과 화면 렌더  |
| GET | `/payments/view/fail`| 결제 실패 시 화면 이동|

➡️ [📑 API 상세 명세 보기 (Notion)](https://unique-income-725.notion.site/BookShop-API-214dee6a325180abba7bfe1c49af9e8e?source=copy_link)


## 🗂️ 디렉토리 구조 (요약)

```bash
📦 src
 ┣ 📂main
 ┃ ┣ 📂config         → 설정 클래스
 ┃ ┣ 📂controller     → REST API 컨트롤러
 ┃ ┣ 📂domain         → 핵심 도메인 객체 및 연관관계 설정
 ┃ ┣ 📂dto            → 요청/응답 DTO
 ┃ ┣ 📂exception      → 예외 처리
 ┃ ┣ 📂repository     → JPA Repository
 ┃ ┣ 📂service        → 핵심 비즈니스 로직
 ┗ 📂test             → 단위 테스트
``` 

## 📌 프로젝트 목표 및 학습 포인트
- JPA 연관관계 매핑 (단방향/양방향)
- RESTful URL 설계 및 HTTP 메서드 활용
- 요청/응답 DTO 사용 및 API 명세화
- 결제 시 @RequestParam으로 값 받음 -> 추후 @RequestBody + DTO로 리펙토링


## 📎 기타 참고
- API 문서화는 Swagger 사용 → 아직 자동화까지는 사용x, Markdown 수동 문서화
- CI/CD, 배포는 포함되지 않음 (추후 EC2 + Docker 목표)

## ✅ 시연 영상

> **회원가입, 주문 생성/취소, 상품 등록/수정/삭제** 등 자세한 시연 화면은 [노션 포트폴리오](https://www.notion.so/BookShop-1d2dee6a3251801caf76cca3b5dff517?pvs=97#1d7dee6a3251806da53ce7674a512ed7)에 포함되어 있습니다.

> https://github.com/user-attachments/assets/99201ec4-f544-4481-afa2-336c51e437a2   

> **결제 및 취소**
 
> https://github.com/user-attachments/assets/b30b009c-691f-4eb0-9d7b-d4e31eb67f2e
> 

## 📌 일부 코드 예시 -> 주문 생성 로직 
- 회원, 상품, 배송 정보를 조회하여 하나의 주문 객체로 생성하는 메서드
```

    //주문 생성
    @Transactional
    public Long order(Long memberID, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberID);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }
```

## 📌 일부 코드 예시 -> Toss결제 로직

```
//Toss Payments 결제 승인(확인) 요청 메서드.
public void verifyPayment(String paymentKey, String orderId, int amount) {
        // 1) Toss 결제 승인(확인) API 엔드포인트
        String url = "https://api.tosspayments.com/v1/payments/confirm";

        // 2) HTTP 헤더 구성 (Basic 인증 + JSON 컨텐트 타입)
        HttpHeaders headers = new HttpHeaders();
        String encodedAuth = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3) 요청 바디(JSON) 생성
        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", paymentKey);
        body.put("orderId", orderId);
        body.put("amount", amount);

        // 4) HttpEntity로 헤더와 바디를 묶어 전송 준비
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            // 5) Toss 서버에 결제 승인(확인) 요청 전송 (POST)
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            // 6) 상태 코드가 2xx가 아니면 승인 실패로 간주
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("Toss 결제 확인 실패 : 응답코드 = " + response.getStatusCode());
            }
            // 7) 승인 성공 로그 출력
            log.info("Toss 결제 검증 성공: {} ", response.getBody());
        } catch (HttpClientErrorException e) {
            // 8) Toss가 4xx 에러를 반환한 경우(요청 파라미터 오류, 이미 승인된 결제 등)
            log.info("Toss 결제 확인 실패 (HTTP 오류): {}", e.getResponseBodyAsString(), e);
            throw new IllegalStateException("결제 검증 실패: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            // 10) 네트워크 문제, 타임아웃, 직렬화 문제 등 그 외 예외 처리
            log.info("Toss 결제 확인 중 예외 발생", e);
            throw new RuntimeException("Toss 결제 확인 중 오류: " + e.getMessage(), e);
        }
    }
```




