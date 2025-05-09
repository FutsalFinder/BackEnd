현재 백엔드 도메인
: https://d3sycde725g6va.cloudfront.net/

# BackEnd 개발일지

<2024-03-28>
http://localhost:8080/futsal-info/{yyyy-mm-dd}
위 URL에 HTTP GET 요청을 할 시, [플랩풋볼, 위드풋살, 퍼즐풋볼, 아이엠그라운드 소셜매치] 4개의 플랫폼의 서울 지역 모든 풋살 매치 데이터를 통일된 Json 형식으로 받을 수 있는 서버를 구현하였다.

---
<2024-05-10>
http://futsalfinder.store:8080 으로 백엔드 서버 배포완료.
다만, 프론트엔드 배포한 Vercel에서 http 통신은 지원하지 않는 문제 발생.

이에 AWS Route 53 DNS 등록 -> ACM 인증서 발급 -> AWS CloudFront 배포를 통해 우리 서버 앞단에 CloudFront를 배치하여 Https 통신을 이루어 냈고, 또한 캐싱을 통해 속도 개선.

---
<2025-05-04>
feat: Improve/performance (#7)
- I/O Bound, CPU Bound 작업 분리 (request / parse, transform)
- I/O 전용 스레드 풀 생성 및 request 요청 할당
- CPU Bound 작업은 ForkJoinPool.commonPool 에 할당 (코어 수와 같은 사이즈의 스레드풀)

---
<2025-05-09>
feat: 프론트엔드 페이지 개발, 어반풋볼 추가 (#8)
<img width="1500" alt="image" src="https://github.com/user-attachments/assets/3faa3e10-4ac4-47cf-a4aa-a041274c8a23" />

- Javascript, Thymeleaf 활용하여 플랫폼 정보를 포함한 기본 웹페이지를 서버에서 렌더링하여 응답
- 이후 매치 데이터 API 요청하여 클라이언트에서 렌더링 및 필터링
- 어반풋볼 추가 및 플랩풋볼 '게스트 모집' 매치 추가 및 링크 오류 수정
- 반응형 웹은 추후 구현

---
<2025-05-09>
feat: 잔여인원 필터링 기능 추가 (#9)
<img width="1508" alt="image" src="https://github.com/user-attachments/assets/b7521700-cd91-495c-aad9-889f0e48ca67" />
- 설정한 잔여인원 이상 매치만 보이기 기능 추가

