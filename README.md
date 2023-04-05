![MyomiBanner2](https://user-images.githubusercontent.com/103922744/230006974-1f128fe7-bea4-4437-85fb-078e4755385a.png
)

<br>
<br>

## 📢 목차
* [기획의도](#기획의도)
* [소개](#프로젝트-소개)
* [기능](#기능)
* [구성](#구성)
* [기술 스택](#기술-스택)
* [과정](#과정)
* [미리보기](#페이지-미리보기)
  <br>
  <br>

## 기획의도
1. 한번 올라가면 내려올 줄 모르는 치솟는 물가, 매일 밖에서 사먹는게 부담스럽지는 않으신가요? <br>
2. 그렇다고 퇴근 후 지친 몸으로 장을 보고 재료를 손질해 나의 한끼를 챙기는게 힘드셨나요? <br>
3. 맨날 똑같은 한끼 식사가 질리지는 않으신가요? <br>

알뜰한 현대인들의 지갑을 챙겨줄 식품 정기배송 플랫폼에 대한 목마름으로 기획하게 되었습니다.

## 프로젝트 소개
### 묘미(妙味) : 묘할 묘, 맛 미 |  신비롭고 좋은 맛
샐러드 / 도시락 /  밀키트 주 단위 정기배송 서비스입니다. <br>
따로 따로 주문하며 배송비도, 적립금도, 회원 혜택도 놓치셨다면
묘미에서 맛과 혜택, 둘 다 즐겨보시는 건 어떠세요?

- 다양한 제품을 여러 판매자의 제품과 비교후 필요한 상품을 구매할 수 있습니다.
- 샐러드 / 도시락 /  밀키트를 한번에 주문이 가능합니다.
- 배송 수령일을 선택하여, 원하는 날짜에 상품을 받아볼 수 있습니다.
- 직접 판매자로 신청하여 묘미의 파트너가 되어보세요.
  <br>
  <br>

## 기능
| 카테고리 |                기능                 | 기능 내용                                                       |
| :------: |:---------------------------------:|:------------------------------------------------------------|
| 회원 | 회원가입 </br> 로그인/로그아웃| Spring Security <br> JWT + Refresh Token <br> Cookies 라이브러리(F)  |
| 주문 |                CR                | 주문, 주문조회  |
| 결제 |                CRD                | IAMPORT API  |
| 채팅 |             CR               | Web Socket & STOMP |
| 게시글 |               CRUD                | 게시글 작성, 조회, 수정, 삭제 |
| 댓글 |                CRUD                | 댓글 작성, 조회, 수정, 삭제 |
| 상품 |                CRU                | 상품 등록, 조회, 수정  |
| 장바구니 |                CRUD                 | 장바구니 추가, 조회, 수정, 삭제  |
| 관리자 |             연관기능             | 판매자 신청 조회, 승인, 공지사항 작성  |
| 공지사항 |              CRUD               | 공지사항 작성, 조회, 수정, 삭제 |
<br>
<br>

## 구성
#### ERD
<img width="948" alt="스크린샷 2023-03-16 15 28 52" src="https://user-images.githubusercontent.com/103922744/225533341-00f4fe83-9b30-405a-a924-4f45f85efd5d.png">


#### Architecture Version 1
<img width="948" alt="아키텍처 ver1 5" src="https://user-images.githubusercontent.com/103922744/225532763-3a3f854f-9f41-4097-849b-0780acdaa751.png">

<br>
<br>

## 기술 스택
| Backend | <img src="https://img.shields.io/badge/JAVA-E85C33?style=for-the-badge&logo=Python&logoColor=white">  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-137CBD?style=for-the-badge&logo=Spring Security&logoColor=white"> <img src="https://img.shields.io/badge/JPA-E97627?style=for-the-badge&logo=JPA&logoColor=white"> <img src="https://img.shields.io/badge/Query DSL-0096D6?style=for-the-badge&logo=Query DSL&logoColor=white"> <img src="https://img.shields.io/badge/WebSocket(STOMP)-FF6A00?style=for-the-badge&logo=WebSocket(STOMP)&logoColor=white"> <img src="https://img.shields.io/badge/Apache Maven-C71A36?style=for-the-badge&logo=Apache Maven&logoColor=white">                                                                                                                                                                                                |
| ------ |:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Frontend | <img src="https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jQuery&logoColor=white"> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"> <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=Bootstrap&logoColor=white"> <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"> <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white">       |
| DB | <img src="https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=Oracle&logoColor=white">                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| CLOUD | <img src="https://img.shields.io/badge/Amazon Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">                                                                                                                                                                                                                                                     |
| ETC | <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"> <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub Actions&logoColor=white">                                                                                                                                                                                                                                                                                                                                                                                                 |
<br>
<br>

## 과정

<img width="1246" alt="스크린샷 2023-03-24 15 27 59" src="https://user-images.githubusercontent.com/103922744/227442600-110ddedc-9ff3-4271-b9a6-7941cb0f03f7.png">

<br>
<br>

## 페이지-미리보기
<img width="1440" alt="스크린샷 2023-03-24 14 13 07" src="https://user-images.githubusercontent.com/103922744/227443038-de23d63c-998a-441e-9c6e-15942e499145.png">

<br>
