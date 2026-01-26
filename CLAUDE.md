# CLAUDE.md

이 파일은 Claude Code (claude.ai/code)가 이 저장소에서 작업할 때 참고하는 가이드입니다.

## 빌드 및 실행 명령어

```bash
# 빌드 (테스트 제외)
./gradlew build -x test

# 테스트 실행
./gradlew test

# 클린 빌드
./gradlew clean build

# 애플리케이션 로컬 실행 (환경 변수 필요)
./gradlew bootRun
```

## 프로젝트 개요

**대동여지도 BE v4.0** - 학교 동아리 관리 플랫폼의 Spring Boot 3.2.5 백엔드 애플리케이션. Java 17 사용.

### 기술 스택
- **데이터베이스**: MySQL + JPA/Hibernate + QueryDSL
- **캐시/큐**: Redis (캐싱, 스케줄러 페이로드, 리프레시 토큰)
- **인증**: JWT 기반 인증
- **외부 연동**: AWS S3 (파일 저장), Solapi SMS, Feign 클라이언트
- **모니터링**: Actuator를 통한 Prometheus 메트릭

## 아키텍처

### 패키지 구조

```
team.jeonghokim.daedongyeojido/
├── domain/           # 비즈니스 도메인 (DDD 스타일)
│   ├── admin/        # 관리자 관리
│   ├── alarm/        # 사용자 알림
│   ├── announcement/ # 동아리 공고
│   ├── application/  # 지원서 폼 및 제출
│   ├── auth/         # 인증
│   ├── club/         # 동아리 관리 (핵심 도메인)
│   ├── resultduration/ # 결과 발표 시간
│   ├── schedule/     # 면접 일정
│   ├── submission/   # 지원서 제출
│   └── user/         # 사용자 관리
├── global/           # 공통 관심사
│   ├── cache/        # 캐시 키 상수
│   ├── config/       # Spring 설정 (Security, QueryDSL, JWT)
│   ├── entity/       # 베이스 엔티티 클래스
│   ├── error/        # 예외 처리
│   └── security/     # JWT 필터, 인증
└── infrastructure/   # 외부 연동
    ├── event/        # 비동기 이벤트 리스너 (알림, SMS)
    ├── feign/        # 외부 API 클라이언트
    ├── redis/        # Redis 설정
    ├── s3/           # AWS S3 파일 작업
    ├── scheduler/    # 스케줄 작업 처리
    └── sms/          # Solapi SMS 연동
```

### 도메인 레이어 패턴

각 도메인은 일관된 구조를 따름:
```
domain/{name}/
├── domain/           # 엔티티, enum, 리포지토리
├── exception/        # 도메인별 예외
├── facade/           # 엔티티 조회 헬퍼 (NotFoundException throw)
├── presentation/     # 컨트롤러, DTO (request/response)
└── service/          # 비즈니스 로직 서비스
```

### 예외 패턴

커스텀 예외는 `DaedongException`을 상속하며 정적 싱글톤 인스턴스 사용:
```java
public class ClubNotFoundException extends DaedongException {
    public static final DaedongException EXCEPTION = new ClubNotFoundException();
    private ClubNotFoundException() {
        super(ErrorCode.CLUB_NOT_FOUND);
    }
}
// 사용법: throw ClubNotFoundException.EXCEPTION;
```

모든 에러 코드는 `global/error/exception/ErrorCode.java`에 정의됨.

### 비동기 이벤트 시스템

알림을 위한 이벤트 기반 아키텍처:
- `ApplicationEventPublisher`로 이벤트 발행
- 리스너는 `@TransactionalEventListener(phase = AFTER_COMMIT)`와 `@Async` 사용
- User/Club 알림과 SMS 이벤트 분리 (단건 및 대량)
- Spring Retry로 일시적 실패 처리

### 캐싱

`global/cache/CacheNames.java`에 정의된 키로 Redis 캐싱. 서비스에서 `@Cacheable` 어노테이션 사용.

### 스케줄 작업

결과 발표 시간에 SMS/알림 일괄 전송을 위한 Redis ZSet 기반 스케줄링. ShedLock으로 분산 락 관리.

## 환경 변수

필수 설정 (`application.yml` 참고):
- 데이터베이스: `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`
- Redis: `REDIS_HOST`, `REDIS_PORT`
- JWT: `JWT_HEADER`, `JWT_PREFIX`, `JWT_SECRET_KEY`, `JWT_ACCESS_EXP`, `JWT_REFRESH_EXP`
- AWS: `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `AWS_REGION`, `BUCKET_NAME`, `DAEDONG_IMAGE_FOLDER`
- SMS: `ACCESS_KEY`, `SECRET_KEY`, `REPRESENTER_PHONE_NUMBER`, `SOLAPI_URL`
- 외부 연동: `XQUARE_URL` (로그인 API)