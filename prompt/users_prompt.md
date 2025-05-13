## AI 활용 프롬프트 모음

### ✅ 1. SQLite3와 Spring Boot 3.x (Hibernate 6.x)의 호환 Dialect 구현
> Cannot resolve method 'registerColumnType' in 'SQLiteDialect' 에러를 해결할 수 있게, Hibernate 6에 맞춘 contributeTypes 기반의 SQLiteDialect를 작성해줘

### ✅ 2. SQLite용 Hibernate 공식 Dialect 라이브러리 적용
> org.hibernate.dialect.SQLiteDialect 클래스가 없다고 나와. Spring Boot 3.4.5에서 쓸 수 있는 hibernate-community-dialects 라이브러리 버전과 설정 방법 알려줘

### ✅ 3. JPA에서 Entity 데이터를 SQLite로 저장했는데 안 들어가는 이유 디버깅
> userRepository.save(user) 했는데 todo.db에 저장이 안 돼. 그럴 때 원인 5가지를 알려줘

### ✅ 4. 비밀번호를 직접 암호화할 때 BCryptPasswordEncoder 없이 jBCrypt로 처리
> 스프링 시큐리티 없이 비밀번호 암호화하려면 뭐 써야 돼? BCryptPasswordEncoder 말고 jBCrypt로 처리하는 방식 알려줘

### ✅ 5. 리프레시 토큰 도입 필요 여부 판단
> 로그인하면 access_token 주는건 구현했는데, 과제에서 리프레시 토큰까지 구현할 필요가 있을까?

### ✅ 6. JWT 비밀 키(SECRET_KEY) 보관 위치와 방법
> SECRET_KEY는 어디다 저장하는게 좋음?

### ✅ 7. 회원가입 후 자동 로그인 + JWT 발급 여부에 대한 UX 고민
> 굳이 회원가입 후에 토큰을 줘야 하나? 보통 로그인은 따로 하는 거 아니야?

### ✅ 8. 탈퇴 처리 시 실제 삭제 vs soft delete 정책 고민
> DELETE를 만들라는데... 이미 use_yn 필드로 상태 관리하려고 했는데 DELETE면 진짜 삭제해야 하나?