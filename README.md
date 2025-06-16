# 📚 BookShop (사이드 프로젝트)

> 간단한 상품 주문 사이트 -> 스프링 부트 + 스프링 데이터 JPA + RESTful API

> 📬 이메일: heyfer6867@gmail.com  
> 💼 포트폴리오: https://unique-income-725.notion.site/BookShop-1d2dee6a3251801caf76cca3b5dff517?source=copy_link
--- 

## 💡 프로젝트 소개
`실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발` 강의를 참고하여 만든 프로젝트입니다.  
스프링부트와 RESTful API 구조로 주문 흐름에 대한 이해를 목표로 하며, 도서 등록, 주문, 회원 관리 등 전반적인 웹 애플리케이션의 기능을 백엔드 중심으로 구현했습니다.

## ⏱️개발 기간
- **2025.04.07 ~ 04.27**

- ## ⚙️ 개발 환경 및 기술 스택

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

--- 
# 주요 API 엔드포인트 목록

### 📌 Order API

| Method | URL | 설명 |
| --- | --- | --- |
| GET | `/api/orders` | 주문 목록 조회 |
| POST | `/api/orders` | 주문 생성 |
| PATCH | `/api/orders/{id}/cancel` | 주문 취소 |
| GET | `/api/orders/{id}` | 주문 상세 |

### 📌 Member API

| Method | URL | 설명 |
| --- | --- | --- |
| GET | `/api/members` | 회원 목록 조회 |
| POST | `/api/members` | 회원등록 |
| DELETE | `/api/members/{id}` | 회원삭제 |
| PATCH | `/api/members/{id}` | 회원 수정 |

### 📌 Item API

| Method | URL | 설명 |
| --- | --- | --- |
| GET | `/api/items` | 모든 상품 조회 |
| POST | `/api/items` | 상품 등록 |
| GET | `/api/items/{id}` | 상품 단건 조회 |
| DELETE | `/api/items/{id}` | 상품 삭제 |
| PATCH | `/api/items/{id}` | 상품 수정 |

--- 

## 🧩 주요 기능 / 시연 이미지

### ✅ 회원 기능
- 회원가입 및 로그인
- 주문 내역 확인
- 주문 시 수량만큼 재고 자동 차감

  <img src="https://github.com/user-attachments/assets/e705f364-f041-4baf-acb4-664185914f57" width="450"/>
  <img src="https://github.com/user-attachments/assets/01429053-e224-47a5-9589-a0b5f6779c6c" width="450"/> 
  <img src="https://github.com/user-attachments/assets/9e50f650-b58e-447d-a5e5-2f6e5621ddfa" width="450"/>


### ✅ 주문 기능
- 주문 생성 / 취소
- 주문 내역 검색 기능 제공

  <img src="https://github.com/user-attachments/assets/ae85badb-58c3-4679-be4e-075315eeef7b" width="450"/>
  <img src="https://github.com/user-attachments/assets/b7071085-dec7-4927-a9be-ce9be7dd5df0" width="450"/>
  <img src="https://github.com/user-attachments/assets/b17c7185-6aa5-4d25-bd9d-95ef58095a9a" width="450"/>


### ✅ 상품 기능 (관리자 전용)
- 도서 등록 / 수정 / 삭제
- 전체 주문 내역 확인

  <img src="https://github.com/user-attachments/assets/16dcf8fc-b72d-4bed-b32f-2b0f6677f456" width="450"/>
  <img src="https://github.com/user-attachments/assets/a42625cc-5236-493c-b749-ef6ccc634a2f" width="450"/>
  <img src="https://github.com/user-attachments/assets/c67d0ca0-a10f-4e43-a4bf-4d3584753404" width="450"/>
  <img src="https://github.com/user-attachments/assets/dbceedae-acf2-493a-bae0-599bbe28a7ff" width="450"/>


## 🎬 시연 화면

<img src="https://github.com/user-attachments/assets/a36934d9-7dfe-4592-89cf-a38b00f0ac20" width="500"/>  
<img src="https://github.com/user-attachments/assets/45e21d70-ce5a-456f-80ae-43e375f899f8" width="500"/>
<img src="https://github.com/user-attachments/assets/e9c34d36-5d40-44ab-9ccf-aa31aee4d0ce" width="500"/>


## ✍️ 개발 후기

- JPA의 연관관계 매핑과 주문 흐름 구현을 통해 도메인 설계에 대한 기초를 다졌습니다.
- Thymeleaf를 통한 MVC방식으로도 실습해보고 화면 출력과 컨트롤러 간 역할 분리를 배웠습니다.
- 기존 MVC방식에서 RESTful API 설계 방식으로 리펙토링하여 기본 구조를 실습할 수 있는 좋은 경험이었습니다.


