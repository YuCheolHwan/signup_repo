# SignUp (Membership registration) and e-mail writing
  * 검색 엔진 웹사이트의 메일 보내기 기능을 생각하여 메일 시스템 구현.
  * 메일을 보내기 위하여 회원가입 진행 후 시스템 진행.
  * 회원가입 완료 시 각 아이디마다 테이블 생성됨(번호, 보낸 시간, 발송인, 수령인, 내용)
## 개발 언어
<img src="https://img.shields.io/badge/-Java-orange">
<img src="https://img.shields.io/badge/-MySql-blue">

## 개발 환경
| Category | Content |
| --- | --- |
| OS | Windows 10 Home |
| Language | Java 17.0.6 |
| Editor | Eclipse IDE 2022-06 (4.24.0) |
| DBMS | MySQL Workbench 8.0.17 |
| Github | https://github.com/YuCheolHwan/signup_repo |

## 개발 기간
2023-03-03(Fri) ~ 2023-03-04(Sat)

### 프로젝트 설명
1. 회원가입
2. 로그인 (로그인 후 개인 로그인 시스템으로 이동) 

   2-1. 메일 작성 
 
   2-2. 메일 확인 
 
   2-3. 회원 정보 수정(비밀번호, 이름, 생년월일, 거주 지역) 
 
   2-4. 회원 탈퇴 
 
3. 아이디/비밀번호 찾기
4. 종료

#### MySql table
![image](https://user-images.githubusercontent.com/126849378/224650921-dab9af8a-ae6d-48d0-aceb-b93fa757b0dc.png)
![image](https://user-images.githubusercontent.com/126849378/224651048-0bd342fc-d380-4c85-a25a-c415a050f81e.png)


#### 프로젝트 실행 결과
◎ 회원가입  
![image](https://user-images.githubusercontent.com/126849378/224635162-2778b999-d792-4944-a523-eaa1463ac22b.png)

◎ 로그인  
![image](https://user-images.githubusercontent.com/126849378/224635425-43aefba8-6ea6-4048-8ee1-0549b840804d.png)

⊙ 메일 작성  
![image](https://user-images.githubusercontent.com/126849378/224635867-8594e2ee-cabe-4db5-872f-9a49c29536d7.png)

⊙ 메일 확인 (로그아웃 후 발송한 아이디로 재 로그인 후 확인)  
![image](https://user-images.githubusercontent.com/126849378/224636297-effeec4a-b02a-4926-b245-595644fcbd16.png)

⊙ 회원 정보 수정 (비밀번호, 이름, 생년월일, 거주 지역)

 * 비밀번호 수정 (비밀번호 수정 후 자동 로그아웃)
 ![image](https://user-images.githubusercontent.com/126849378/224638519-23c0f415-5df9-461c-aba0-fb62ac4eac6a.png)  
  
 * 이름 수정  
 ![image](https://user-images.githubusercontent.com/126849378/224644741-09fbca5b-f84c-4e09-aa2d-344c97acc351.png)  
  
 * 생년월일 수정  
 ![image](https://user-images.githubusercontent.com/126849378/224645093-da562c98-dbaa-498e-9d54-18151d5592ca.png)  
  
 * 거주지역 수정  
 ![image](https://user-images.githubusercontent.com/126849378/224645534-92e4713b-9729-4c86-8747-1656d96f9c3f.png)  
  
⊙ 회원 탈퇴  
 ![image](https://user-images.githubusercontent.com/126849378/224647467-10c0e190-4448-44a7-9054-d8533655564a.png)

◎ 아이디/패스워드 찾기  
 ![image](https://user-images.githubusercontent.com/126849378/224648615-e10cef68-a823-40d6-9af2-09d6fb72334d.png)

◎ 종료  
