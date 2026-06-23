# 💻 DevAsk - 미니 Q&A 커뮤니티

**DevAsk**는 개발자들이 지식을 공유하고 질문과 답변을 통해 함께 성장하는 **회원 중심의 미니 Q&A 커뮤니티**입니다. Spring Security와 JWT를 활용한 탄탄한 인증/인가 아키텍처를 기반으로 하며, 사용자 경험을 고려한 '반오픈형(조회 오픈, 활동 제한)' 구조와 안전한 비밀글 및 댓글 시스템을 제공합니다.

---

## 📌 프로젝트 개요
* **프로젝트명**: DevAsk (미니 Q&A 커뮤니티) 
* **목적**: Spring Boot 기반의 백엔드 에코시스템(Security, JWT, JPA, QueryDSL)을 깊이 있게 이해하고, 실제 서비스 수준의 권한 제어 및 동시성 처리를 학습하기 위한 프로젝트입니다.
* **주요 특징**: 
  * 일반적인 조회 기능은 비회원에게도 제공하되, 데이터 변경 행위는 철저히 JWT 토큰으로 통제하는 **'반오픈형' 구조** 채택 
  * BCrypt 단방향 암호화를 적용한 안전한 **비밀글 시스템** 구현 
  * **Self-Reference(셀프 참조)** 구조를 활용한 계층형 대댓글 아키텍처

---

## 🛠 기술 스택
* **Language**: Java 
* **Framework**: Spring Boot, Spring Security 
* **Authentication**: JWT (JSON Web Token) 
* **Build Tool**: Gradle 
* **Persistence**: JPA (Hibernate), QueryDSL 
* **Database**: PostgreSQL
* **DevOps/Infrastructure**: Docker (서버 배포 환경), Redis (조회수 동시성 제어 활용 가능)
* **Documentation**: Swagger (API 명세 및 테스트 자동화)

---

## ✨ 주요 기능

### 1. 회원 관리 (User Management)
* **인증/인가**: Spring Security + JWT 기반의 Stateless 인증 체계.
* **아이디 중복 검사**: 회원가입 전용 별도 API 제공 및 백엔드 로직 내 2차 검증을 통한 무결성 보장.
* **비밀번호 암호화**: `PasswordEncoder(BCrypt)`를 활용한 단방향 암호화 처리.
* **보안 강화**: 로그인 5회 실패 시 계정 잠금(`ACCOUNT_LOCK = 'Y'`) 처리 및 성공 시 실패 카운트 리셋.
* **권한 분리**: 일반 사용자(`ROLE_USER`)와 관리자(`ROLE_ADMIN`) 역할 분리.

### 2. 질문 (Question CRUD) & 비밀글 시스템
* **질문 관리**: 최신순, 인기순 정렬 조건이 반영된 질문 목록 및 상세 조회 기능.
* **다대다 태그 필터링**: 중간 테이블을 둔 질문과 태그 간의 다대다($N:M$) 관계 설계를 통한 태그 기반 필터링.
* **조회수 동시성 제어**: 다수의 사용자가 동시에 조회할 때 발생하는 데이터 정합성 문제를 방지하기 위한 동시성 처리 (Redis/DB Lock 확장).
* **안전한 비밀글**: 
  * 비밀글 설정 시 패스워드 재사용 습관을 고려하여 비밀번호를 BCrypt로 암호화 저장.
  * 관리자나 작성자 본인은 인증 토큰을 통해 비밀번호 없이 프리패스로 조회 가능.
  * 제3자 혹은 비회원이 접근 시 비밀번호 검증(`passwordEncoder.matches()`) 로직 수행 및 목록 조회 시 타이틀 마스킹 처리.

### 3. 답변(Answer) 및 댓글(Comment) 시스템
* **답변(Answer)**: 하나의 질문에 여러 답변이 달릴 수 있는 $1:N$ 구조 및 질문자가 가장 적절한 답변을 채택하는 **Best 답변 선택 기능**.
* **계층형 댓글**: JPA의 self-reference 관계 설계를 활용하여 대댓글(2단계 계층형 구조)까지 깔끔하게 구현.

### 4. 좋아요(Vote) 기능
* **중복 방지**: 사용자당 하나의 질문에 한 번만 투표할 수 있도록 중복 방지 로직 설계.
* **비회원 방어 코드**: 반오픈형 구조에 맞춰 비회원이 질문 상세 조회 시 '좋아요 여부'를 무조건 `false`로 안전하게 리턴하도록 분기 처리.

---

## 📂 프로젝트 구조 (Project Architecture)

일반적인 Spring Boot 가이드라인과 도메인 주도 설계(DDD) 혹은 계층형 아키텍처 관례를 따른 표준 구조 예시입니다. (실제 생성하신 패키지명에 맞게 일부 수정하여 사용하세요.)

```text
src/main/java/com/devask
│
├── global                  # 공통 설정 및 보안 관련 패키지
│   ├── config              # SecurityConfig, PasswordConfig, SwaggerConfig 등
│   ├── security            # JWT 토큰 프로바이더, 필터, 유저 디테일 서비스
│   └── error               # 글로벌 예외 처리 (Exception Handler, Custom Error Code)
│
├── domain                  # 비즈니스 도메인별 패키지
│   ├── user                # 회원 도메인
│   │   ├── controller      # UserController (회원가입, 로그인, 중복 체크)
│   │   ├── entity          # User (T_USER 테이블 매핑)
│   │   ├── repository      # UserRepository
│   │   └── service         # UserService (로그인 실패 카운트, 잠금 로직 포함)
│   │
│   ├── question            # 질문 도메인
│   │   ├── controller      # QuestionController (목록, 상세, 비밀글 조회)
│   │   ├── entity          # Question, Tag, QuestionTag (다대다 중간 테이블)
│   │   ├── repository      # QuestionRepository (QueryDSL custom 인터페이스 포함)
│   │   └── service         # QuestionService (비밀글 검증, 동시성 조회수 처리)
│   │
│   ├── answer              # 답변 도메인
│   │   └── ...             # Controller, Entity, Service, Repository
│   │
│   └── comment             # 댓글 도메인
│       ├── entity          # Comment (Parent-Child 셀프 참조 엔티티 구조)
│       └── ...
│
└── DevAskApplication.java  # 메인 애플리케이션 실행 클래스