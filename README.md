# 📚 BookShop - 주문 관리 시스템 (RESTful API 프로젝트)

**Java / Spring Boot / Spring Data JPA / REST API 기반 사이드 프로젝트**

> 📬 이메일: heyfer6867@gmail.com  
> 💼 포트폴리오: [자세히 보기 (Notion)](https://unique-income-725.notion.site/BookShop-1d2dee6a3251801caf76cca3b5dff517?source=copy_link)

## 💡 프로젝트 소개
> `실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발` 강의를 참고하여 만든 프로젝트입니다.  
> 상품 주문 흐름을 백엔드 중심으로 구현하고 RESTful 설계 패턴과 JPA 연관관계 매핑을 실습하기 위해 제작했습니다.


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
 ┃ ┣ 📂config         → 설정 클래스(Spring 설정 등)
 ┃ ┣ 📂controller     → REST API 컨트롤러
 ┃ ┣ 📂domain         → 엔티티 및 도메인 로직
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

## 🧩 주요 기능 / 시연 이미지

### ✅ 회원 기능
- 회원가입 및 로그인
- 주문 내역 확인
- 주문 시 수량만큼 재고 자동 차감

  <img src="https://github.com/user-attachments/assets/e705f364-f041-4baf-acb4-664185914f57" width="600"/>
  <img src="https://github.com/user-attachments/assets/01429053-e224-47a5-9589-a0b5f6779c6c" width="600"/>
  <img src="https://github.com/user-attachments/assets/9e50f650-b58e-447d-a5e5-2f6e5621ddfa" width="600"/>


### ✅ 주문 기능
- 주문 생성 / 취소
- 주문 내역 검색 기능 제공

  <img src="https://github.com/user-attachments/assets/ae85badb-58c3-4679-be4e-075315eeef7b" width="600"/>
  <img src="https://github.com/user-attachments/assets/b7071085-dec7-4927-a9be-ce9be7dd5df0" width="600"/>
  <img src="https://github.com/user-attachments/assets/b17c7185-6aa5-4d25-bd9d-95ef58095a9a" width="600"/>


### ✅ 상품 기능 (관리자 전용)
- 도서 등록 / 수정 / 삭제
- 전체 주문 내역 확인

  <img src="https://github.com/user-attachments/assets/16dcf8fc-b72d-4bed-b32f-2b0f6677f456" width="600"/>
  <img src="https://github.com/user-attachments/assets/a42625cc-5236-493c-b749-ef6ccc634a2f" width="600"/>
  <img src="https://github.com/user-attachments/assets/c67d0ca0-a10f-4e43-a4bf-4d3584753404" width="600"/>
  <img src="https://github.com/user-attachments/assets/dbceedae-acf2-493a-bae0-599bbe28a7ff" width="600"/>


## 🎬 시연 화면

<img src="https://github.com/user-attachments/assets/a36934d9-7dfe-4592-89cf-a38b00f0ac20" width="600"/>
<img src="https://github.com/user-attachments/assets/45e21d70-ce5a-456f-80ae-43e375f899f8" width="600"/>
<img src="https://github.com/user-attachments/assets/e9c34d36-5d40-44ab-9ccf-aa31aee4d0ce" width="600"/>


## ✍️ 개발 후기

- JPA의 연관관계 매핑과 주문 흐름 구현을 통해 도메인 설계에 대한 기초를 다졌습니다.
- Thymeleaf를 통한 MVC방식으로도 실습해보고 화면 출력과 컨트롤러 간 역할 분리를 배웠습니다.
- 기존 MVC방식에서 RESTful API 설계 방식으로 리펙토링하여 기본 구조를 실습할 수 있는 좋은 경험이었습니다.


