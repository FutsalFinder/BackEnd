# BackEnd 개발일지

<2024-03-28>
http://localhost:8080/futsal-info/{yyyy-mm-dd}
위 URL에 HTTP GET 요청을 할 시, [플랩풋볼, 위드풋살, 퍼즐풋볼, 아이엠그라운드 소셜매치] 4개의 플랫폼의 서울 지역 모든 풋살 매치 데이터를 통일된 Json 형식으로 받을 수 있는 서버를 구현하였다.

----------------------------------------------------------------------------
<2024-05-10>
http://futsalfinder.store:8080 으로 백엔드 서버 배포완료.
다만, 프론트엔드 배포한 Vercel에서 http 통신은 지원하지 않는 문제 발생.

이에 AWS Route 53 DNS 등록 -> ACM 인증서 발급 -> AWS CloudFront 배포를 통해 우리 서버 앞단에 CloudFront를 배치하여 Https 통신을 이루어 냈고, 또한 캐싱을 통해 속도 개선.

https://match.futsalfinder.store  <<< 최종 백엔드 서버 도메인
