## ReciPT - Backend

---

- 팀명 : 삼다수

- 팀원 : 손지석, 이재현, 남혁준

- 작품명 : ReciPT (Recipe + GPT)

- 주제 : 유저가 입력한 식재료를 통해 GPT를 통한 레시피 추천 서비스

- 기존 레시피 어플의 문제점
  1. 사용자 취향 반영된 레시피 추천 기능 없음
  2. 재료 기반 추천 기능의 불편함
  3. 등록되지 않은 레시피 조회 불가
  4. 독특한 레시피 등록의 비효율성

- 해결 방안
  1. 취향 반영 레시피 추천 기능 & 없는 레시피 조회 불가능한 문제 - GPT
  2. 재료 기반 추천 기능의 불편함 - YoloV8

- 개발 목표
  1. 챗봇 형태의 GPT와 자유로운 대화를 통해 사용자의 취향이 반영된 레시피 추천 받기 기능
  2. YoloV8을 이용해 카메라를 통해 실시간 식재료 인식 기능 추가
     - GPT를 이용해 인식한 식재료를 포함한 음식 추천
     - 음식 추천시 음식을 만들기 위한 부가 식재료 및 레시피도 함께 추천 
     - 추천 받은 레시피 저장 가능 
     - 추천 받은 레시피 등록시 다른 사용자는 등록한 레시피 조회 및 별점을 남길 수 있음
  4. 조회한 음식의 레시피가 없는 경우
    - GPT에게 조회한 음식의 레시피를 추천 받음
  4. 스프링 시큐리티를 이용해 로그인 기능을 개발
  5. AWS EC2를 이용해 배포
  6. 프로메테우스를 통해 메트릭 수집
  7. 그라파나를 이용해 모니터링

---

### 개발 환경

- ![js](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
- ![js](https://img.shields.io/badge/OpenJDK-white?style=for-the-badge&logo=OpenJDK&logoColor=black)
- ![js](https://img.shields.io/badge/JUnit5-black?style=for-the-badge&logo=JUnit5&logoColor=white)
- ![js](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
- ![js](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
- ![js](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
- ![js](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
- ![js](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
- ![js](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
- ![js](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
- ![js](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)

### 기술 스택

- ORM : JPA + Spring-data-JPA + QueryDsl
- Security : ![js](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)

### Communication

- ![js](https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white)
- ![js](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)

---

#### ReciPT 앱 설명

```
1. `Init Data`를 통해 기본 레시피 제공 (`Init Data` 출처 : 식품의약품안전처의 데이터를 기반으로 선정한 약 50가지 레시피)
2. 레시피 추천 기능
    1. 사용자는 카메라를 통해 식재료를 인식시키거나 타이핑을 통해 식재료 입력
    2. GPT API를 통해 GPT를 통한 입력한 식재료 기반 레시피 추천 
3. GPT와 간단한 대화를 통해 사용자 취향 반영된 레시피 추천 기능
4. 없는 레시피 조회 시 GPT가 레시피 생성 후 제공
3. 추천 받은 레시피를 DB에 저장하고 사용자가 레시피 등록 시 불러오기 기능 제공
4. 사용자가 등록한 레시피 데이터를 축적시켜 기본 레시피외 다양한 레시피 데이터 제공
```

---

### 시스템 구성도
![](SystemConfigDiagram.png)

### 발표자료
https://docs.google.com/presentation/d/19WINOombv6tWuSj0n7uobTmS76nnY2SO9f-o3wpT8WU/edit?usp=sharing

### [ERD 분석 - Figma](https://www.figma.com/file/rJlqqSI2Ssyokn2VRqT2z3/ReciPT-%EB%B6%84%EC%84%9D?type=whiteboard&node-id=0-1&t=0inp0EkyTL42uJTP-0)

### [ERD 분석 - ERDCloud](https://www.erdcloud.com/d/Q7WxraMMoDsuDJS3j)
