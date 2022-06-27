# backend_CS
bellytime 고객용 백엔드 서버

### 프로젝트 설명

주기때마다 내가 먹고 싶은 음식을 알람받고, 설정하세요!


### 핵심기능

1. 로그인을 통한 개인 프로필을 제공합니다
2. 친구, 가게를 팔로우 할 수있고 포스팅을 구독할 수 있습니다.
3. 가까운 가게들을 조회할 수 있고, 필터링하여 정렬 할 수 있습니다.
4. 가게를 예약할 수 있습니다.
5. 친구와 가게 사장님간의 채팅을 할 수 있습니다.
6. 쿨타임을 설정하고 해당 쿨타임에 음식에 대한 알림을 받아 볼 수 있습니다.

### 기술 스택
- 채팅을 위한 websocket(stomp)를 적용하여 실시간 채팅을 구현하였습니다.
- spring security를 이용하여 jwt 기반의 Oauth2로그인이 가능합니다(구글, 네이버, 카카오)
- spring batch와 spring quartz를 이용하여 데이터 베이스 일괄 배치 처리를 하였습니다.
- flyway를 설정하여 DB migration를 고려하였습니다.
- restDocs를 설정하여 테스트 기반의 문서 작성을 고려하였습니다.
- junit 5를 기반으로 test code를 작성하였습니다.
- jacoco를 이용해 코드 커버리지를 측정하였습니다.
- 엘라스틱 서치를 적용하여 빠른 검색을 반환하도록 구현하였습니다.
- redis를 사용하여 캐쉬검색을 가능하게 구현하였습니다.
- S3 와 클라우드 프론트를 사용하여 이미지 업로드가 가능하게 구현하였습니다.
- 젠킨슨를 이용하여 CI/CD를 구축하였습니다.
- HTTPS 인증서를 적용하여 https접속이 가능합니다.

### 현재 구성도
<img width="1126" alt="스크린샷 2022-03-21 오전 9 06 48" src="https://user-images.githubusercontent.com/54499829/159191850-a4cdd64f-1c67-4c46-8a3b-6afd6f185512.png">

### 테이블 스키마
<img width="1052" alt="스크린샷 2022-03-21 오전 9 09 02" src="https://user-images.githubusercontent.com/54499829/159191937-3a6a5be6-457f-4aca-b031-92f0f7076eb9.png">


### 백엔드 기술 스택
1. springboot
2. junit5
3. redis
4. mysql
5. ELK
6. docker
7. spring security
8. spring batch
9. flyway
10. jpa
11. websocket
  
  

