
# 스프링부트 블로그 프로젝트
=======
# JoshuaBlog
---

## 1. 기술스택
- JDK 11
- Springboot 2.X 버전
- JPA
- H2 인메모리 디비 - 방언 MySQL
- JSP
- Security
- AJAX
- JSoup
- 부트스트랩

## 2. 요구사항
> HTTP 메서드를 POST와 GET만 사용한다.

#### 요구사항 1단계
- 회원가입 (완)
- 로그인 (시큐리티) (완)
- 글쓰기 (섬머 노트) (완)
- 게시글 목록보기 (Lazy 전략 - N+1문제 해결) (완)
- 게시글 상세보기 (데이터 뿌리기)
- 썸네일 등록하기 (JSoup)

#### 요구사항 2단계
- 아이디 중복확인
- 비밀번호 동일체크
- 로그인 아이디 기억하기
- 프론트 유효성 검사 (onsubmit)
- 백엔드 유효성 검사 (AOP 등록)
- 글로벌 Exception 처리
- Script 응답 설정

#### 요구사항 3단계
- 회원 프로필 사진 등록
- 회원정보 보기
- 회원정보 수정
- 에러 로그 테이블 관리

#### 요구사항 5단계  (개인 프로젝트)
- Love 테이블 생성
- Reply 테이블 생성
- 연관관계 설정
- 댓글쓰기
- 댓글삭제
- 좋아요 하기
- 좋아요 보기
- 좋아요 보기

## 3. 새롭게 알게된 것들
> 프로젝트를 하면서 새롭게 알게된 것들을 기록

- trace, debug, info, warn, error
- 시큐리티가 있어서 filter X
- WEB-INF 떄문에 MVC패턴 강제
- 들어오는 Dto는 전부 Entity 필요
- Open-In-View = false -> 컨트롤러에서 Lazy-Loading 불가
- Jpa에서 만든 메소드는 내가 제어권을 가지고 있지 않다. 따라서 try-catch를 해야한다.
```java
@Transactional
    public void 회원가입(UserRequest.JoinInDto joinInDto) {
        try {
            joinInDto.setPassword(passwordEncoder.encode(joinInDto.getPassword()));
            
            userRepository.save(joinInDto.toEntity());
        } catch (Exception e) {
            throw new RuntimeException("회원가입 오류 : " + e.getMessage());
        }

    }
```
- iframe: 