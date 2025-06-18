# 📚 BookShop - 주문 관리 시스템
**Java / Spring Boot / Spring Data JPA / REST API 기반 사이드 프로젝트**

> 📬 이메일: heyfer6867@gmail.com  
> 💼 포트폴리오: [자세히 보기 (Notion)](https://unique-income-725.notion.site/BookShop-1d2dee6a3251801caf76cca3b5dff517?source=copy_link)

## 💡 프로젝트 소개
> `실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발` 강의를 참고하여 만든 프로젝트입니다.  
> 이 프로젝트는 강의 예제를 기반으로 시작했지만 단순 구현을 넘어 상품-회원-주문 도메인 간 연관관계 설계, API 설계, 트랜잭션 흐름에 대한 이해를 확장하는 데 목적을 두었습니다.
> 회원가입 및 주문 기능은 Thymeleaf를 통한 서버 렌더링(SSR) 기반이며 관리 기능 및 데이터 조회는 REST API 기반으로 처리됩니다. 추후 프론트-백엔드 완전 분리를 목표로 리팩토링 예정입니다.


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

## 👥 개발 인원 및 기간
#### 개발 기간 - 2025.04.07 ~ 2025.04.27

## 🧩 핵심 기능

- 회원가입 / 로그인 / 주문 내역 확인
- 상품 등록, 수정, 삭제 (관리자 전용)
- 주문 생성 / 취소 + 재고 수량 반영
- REST API 기반 CRUD 구현

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
- Controller-Service-Repository 계층 분리
- 요청/응답 DTO 사용 및 API 명세화


## 📎 기타 참고
- API 문서화는 Swagger 사용 → 아직 자동화까지는 사용x, Markdown 수동 문서화
- CI/CD, 배포는 포함되지 않음 (추후 EC2 + Docker 목표)

## ✅ 시연 영상

> **회원가입, 주문 생성/취소, 상품 등록/수정/삭제** 등 자세한 시연 화면은 [노션 포트폴리오](https://www.notion.so/BookShop-1d2dee6a3251801caf76cca3b5dff517?pvs=97#1d7dee6a3251806da53ce7674a512ed7)에 포함되어 있습니다.

> https://github.com/user-attachments/assets/99201ec4-f544-4481-afa2-336c51e437a2
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

