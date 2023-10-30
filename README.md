## ReciPT - Backend

*****

### 프로젝트 설명 : 유저가 입력한 식재료를 통해 GPT를 통한 레시피 추천 서비스

*****

### 개발 환경

- ![js](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
- <img src="https://img.shields.io/badge/OpenJDK-white?style=for-the-badge&logo=OpenJDK&logoColor=black">
- <img src="https://img.shields.io/badge/JUnit5-black?style=for-the-badge&logo=JUnit5&logoColor=white">
- ORM : JPA
- ![js](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
- ![js](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
- ![js](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
- ![js](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
- ![js](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
- ![js](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white), ![js](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white), ![js](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white) 


### 기술 스택

- ORM : JPA + Spring-data-JPA + QueryDsl
- Security : ![js](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)

### Communication

- ![js](https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white)

*****

#### 팀원 : 이재현, 남혁준

*****

#### ReciPT 앱 설명

```
1. `Init Data`를 통해 기본 레시피 제공 (`Init Data` 출처 : 식품의약품안전처의 데이터를 기반으로 선정한 약 50가지 레시피)
2. 레시피 추천 기능
    1. 사용자는 카메라를 통해 식재료를 인식시키거나 타이핑을 통해 식재료 입력
    2. GPT API를 통해 GPT를 통한 입력한 식재료 기반 레시피 추천
    3. 그 외에도 GPT와 간단한 대화를 통해 알러지 식재료 삭제 및 기존의 추천 레시피에서 식재료 추가 및 삭제 가능
3. 추천 받은 레시피는 DB에 저장하고 사용자가 레시피 등록 기능 및 후기 작성시 사용
4. 사용자가 등록한 레시피 데이터를 축적시켜 기본 레시피외 다양한 레시피 데이터 제공
```

### [ERD 분석 - Figma](https://www.figma.com/file/rJlqqSI2Ssyokn2VRqT2z3/ReciPT-%EB%B6%84%EC%84%9D?type=whiteboard&node-id=0-1&t=0inp0EkyTL42uJTP-0)

### [ERD 분석 - ERDCloud](https://www.erdcloud.com/d/Q7WxraMMoDsuDJS3j)