![header](https://capsule-render.vercel.app/api?type=waving&height=300&color=gradient&text=Trello)
# 소개
이 애플리케이션은 사용자가 커스터마이징 가능한 보드, 작업 그룹, 세부 작업 항목을 생성할 수 있는 프로젝트 및 작업 관리 플랫폼입니다. 
실시간 협업 기능을 강화하여 공유 작업 공간, 실시간 업데이트, 작업 내 통신 채널을 제공합니다. 
사용자 인증 시스템은 로그인 처리뿐만 아니라 세부적인 권한 제어를 구현하여, 관리자가 작업 및 보드에 대한 특정 권한을 가진 맞춤형 역할을 할당할 수 있습니다.
또한, 사용자에게 Slack을 통해 알림을 전송됩니다. 더 나아가, 프로젝트 진행 상황 및 팀 생산성을 추적할 수 있는 분석을 할수 있습니다.

# 🚀 STACK 
**Environment**

![인텔리제이](   https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![깃허브](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)
![깃](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![POSTMAN](https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

**Development**

![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Docker Compose](https://img.shields.io/badge/docker--compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![자바](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![SPRING BOOT](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![SQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)

**Communication**

![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)

# 🤔 Authors

- 김도균(팀장): [@gyun97](https://github.com/gyun97)
- 김경민: [@kkm4512](https://github.com/kkm4512)
- 이정현: [@LJH4987](https://github.com/LJH4987)
- 김현: [@ican0422](https://github.com/ican0422)
- 김태현: [@tae98](https://www.github.com/tae98)

# 🙏 Acknowledgements

 - [Awesome Readme Templates](https://awesomeopensource.com/project/elangosundar/awesome-README-templates)
 - [Awesome README](https://github.com/matiassingers/awesome-readme)
 - [How to write a Good readme](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)

# 🖼️ Wireframe
### [📎FigmaLink](https://www.figma.com/design/tfRF6u9aGluYYLKrnDBEoL/Untitled?node-id=0-1&node-type=canvas&t=Epr6VQubsSxpxFCQ-0)
![Untitled (1)](https://github.com/user-attachments/assets/450ff745-9b4a-4b28-861c-5320dc6b7cce)

# ⚒️ ERD Diagram
![trello erd](https://github.com/user-attachments/assets/437681cb-c23d-4c20-a7ab-f7b6cc268f41)


# 🔖 API Reference
## User
### 사용자 생성
```http
POST http://localhost:8080/users
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Body`     | `String` | email | 
|`Body`     | `String`| password|
| `Body`     | `String` | adminBoolean | 
|`Body`     | `String`| adminToken|

#### Request Example
```http
POST http://localhost:8080/users
{
  "email": "a2@gamil.com",
  "password": "1234!abc"
}
```
#### Response Example
```http
{
  "code": 201,
  "message": "사용자 생성완료",
  "data": {
    "id": 4,
    "email": "a2@gamil.com",
    "createAt": "2024-10-17T14:24:54.304273",
    "modifiedAt": "2024-10-17T14:24:54.304273",
    "role": "USER"
  }
}
```
***

### 사용자 로그인
```http
POST http://localhost:8080/users/login
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Body`     | `String` | email | 
|`Body`     | `String`| password|

#### Request Example
```http
POST http://localhost:8080/users/login
{
  "email": "a2@gamil.com",
  "password": "1234!abc"
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "로그인 완료",
  "data": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwidXNlclJvbGUiOiJVU0VSIiwiZW1haWwiOiJhMkBnYW1pbC5jb20iLCJleHAiOjE3MjkxNDY0NjAsImlhdCI6MTcyOTE0Mjg2MH0.1eUxqnhGXeMyS_i79mtQdSXsVUZjMFG3ABXY-C-dp64"
}
```
***

### 사용자 암호변경
```http
PATCH http://localhost:8080/users/{user_id}/change_password
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Body`     | `String` | oldPassword | 
|`Body`     | `String`| newPassword|

#### Request Example
```http
PATCH http://localhost:8080/users/4/change_password
{
  "oldPassword": "1234!abc",
  "newPassword": "1234!abcd"
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "비밀번호 변경 완료",
  "data": "비밀번호 변경 완료"
}
```
***

### 사용자 회원탈퇴
```http
DELETE http://localhost:8080/users/{user_id}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Body`     | `String` | password | 


#### Request Example
```http
DELETE http://localhost:8080/users/4
{
   "password": "1234!abcd"
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "회원 탈퇴완료",
  "data": "회원탈퇴 완료"
}
```

***

## WorkSpace
### 워크스페이스 생성
```http
POST http://localhost:8080/workspaces
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Body`     | `String` | title | 
|`Body`     | `String`| description|


#### Request Example
```http
POST http://localhost:8080/workspaces
{
  "title": "workspacetitle",
  "description": "myworkdspace"
}
```
#### Response Example
```http
{
  "code": 201,
  "message": "워크스페이스 생성에 성공하였습니다.",
  "data": {
    "id": 1,
    "title": "workspacetitle",
    "description": "myworkdspace",
    "createdAt": "2024-10-17T14:36:10.387565",
    "updatedAt": "2024-10-17T14:36:10.387565"
  }
}
```
***

### 사용자의 워크스페이스 목록 조회
```http
GET http://localhost:8080/workspaces
```

#### Request Example
```http
GET http://localhost:8080/workspaces
```
#### Response Example
```http
{
  "code": 200,
  "message": "워크스페이스 조회에 성공하였습니다.",
  "data": [
    {
      "id": 1,
      "title": "workspacetitle",
      "description": "myworkdspace",
      "memberRole": "string",
      "createdAt": "2024-10-17T14:36:10.387565",
      "updatedAt": "2024-10-17T14:36:13.385465"
    }
  ]
}
```
***

### 워크스페이스 수정
```http
PUT http://localhost:8080/workspaces/{workspacesId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Body`     | `String`| title|
|`Body`     | `String`| description|


#### Request Example
```http
PUT http://localhost:8080/workspaces/1
{
  "title": "editTitle",
  "description": "editDescription"
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "워크스페이스 수정에 성공하였습니다.",
  "data": {
    "id": 1,
    "title": "editTitle",
    "description": "editDescription",
    "createdAt": "2024-10-17T14:36:10.387565",
    "updatedAt": "2024-10-17T14:57:25.489127"
  }
}
```
***
### 워크스페이스 삭제
```http
DELETE http://localhost:8080/{workspacesId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 


#### Request Example
```http
DELETE http://localhost:8080/{workspacesId}
{
  "title": "editTitle",
  "description": "editDescription"
}
```
#### Response Example
```http
{
  "code": 204,
  "message": "워크스페이스 삭제에 성공하였습니다.",
  "data": {}
}
```
***

## Member
### 워크스페이스 멤버 추가
```http
POST http://localhost:8080/workspaces/{workspaceId}/members
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Body`     | `Integer`| userId|

#### Request Example
```http
POST http://localhost:8080/workspaces/2/members
{
  "email": "a233456@gmail.com"
}
```
#### Response Example
```http
{
  "code": 201,
  "message": "멤버 생성에 성공하였습니다.",
  "data": {
    "id": 4,
    "workspaceId": 2,
    "userId": 2,
    "memberRole": "READ_ONLY",
    "createdAt": "2024-10-17T15:18:02.317761",
    "updatedAt": "2024-10-17T15:18:02.317761"
  }
}
```
***
### 워크스페이스 멤버 조회
```http
GET http://localhost:8080/workspaces/{workspaceId}/members
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 

#### Request Example
```http
GET http://localhost:8080/workspaces/2/members
```
#### Response Example
```http
{
  "code": 200,
  "message": "멤버 조회에 성공하였습니다.",
  "data": [
    {
      "id": 2,
      "workspaceId": 2,
      "userId": 5,
      "email": "a3@gmail.com",
      "memberRole": "WORKSPACE_ADMIN",
      "createdAt": "2024-10-17T14:39:25.687355",
      "updatedAt": "2024-10-17T14:39:25.687355"
    },
    {
      "id": 4,
      "workspaceId": 2,
      "userId": 2,
      "email": "a233456@gmail.com",
      "memberRole": "READ_ONLY",
      "createdAt": "2024-10-17T15:18:02.317761",
      "updatedAt": "2024-10-17T15:18:02.317761"
    }
  ]
}
```
***

### 워크스페이스 멤버 역활 변경
```http
PUT http://localhost:8080/workspaces/{workspaceId}/members/{memberId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
| `Path`     | `Integer` | memberId | 

#### Request Example
```http
PUT http://localhost:8080/workspaces/2/members/4
```
#### Response Example
```http
{
  "code": 200,
  "message": "멤버 역할 수정에 성공하였습니다.",
  "data": {
    "id": 4,
    "workspaceId": 2,
    "userId": 2,
    "memberRole": "WORKSPACE_ADMIN",
    "createdAt": "2024-10-17T15:18:02.317761",
    "updatedAt": "2024-10-17T15:18:02.317761"
  }
}
```
***

### 워크스페이스 멤버 삭제
```http
DELETE http://localhost:8080/workspaces/{workspaceId}/members/{memberId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
| `Path`     | `Integer` | memberId | 

#### Request Example
```http
DELETE http://localhost:8080/workspaces/2/members/4
```
#### Response Example
```http
{
  "code": 201,
  "message": "멤버 삭제에 성공하였습니다.",
  "data": {}
}
```
***

## Board
### 보드 생성
```http
POST http://localhost:8080/workspaces/{workspaceId}/boards
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Body`     | `String`| title|
|`Body`     | `String`| backgroundColor|

#### Request Example
```http
POST http://localhost:8080/workspaces/2/boards
{
  "title": "BoardTitle",
  "backgroundColor": "red"
}
```
#### Response Example
```http
{
  "code": 201,
  "message": "보드 생성에 성공하였습니다.",
  "data": {
    "id": 1,
    "title": "BoardTitle",
    "backgroundColor": "red",
    "createdAt": "2024-10-17T15:31:01.256068",
    "updatedAt": "2024-10-17T15:31:01.256068"
  }
}
```
***      
### 보드 목록 조회
```http
GET http://localhost:8080/workspaces/{workspaceId}/boards
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 


#### Request Example
```http
GET http://localhost:8080/workspaces/2/boards
```
#### Response Example
```http
{
  "code": 200,
  "message": "보드 조회에 성공하였습니다.",
  "data": [
    {
      "id": 1,
      "title": "BoardTitle",
      "backgroundColor": "red",
      "createdAt": "2024-10-17T15:31:01.256068",
      "updatedAt": "2024-10-17T15:31:01.256068"
    },
    {
      "id": 2,
      "title": "BoardTitle",
      "backgroundColor": "red",
      "createdAt": "2024-10-17T15:33:18.142355",
      "updatedAt": "2024-10-17T15:33:18.142355"
    }
  ]
}
```
***  
### 보드 단건 조회
```http
GET http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
| `Path`     | `Integer` | boardId | 


#### Request Example
```http
GET http://localhost:8080/workspaces/2/boards/1
{
```
#### Response Example
```http
{
  "code": 200,
  "message": "보드 조회에 성공하였습니다.",
  "data": {
    "id": 1,
    "title": "BoardTitle",
    "backgroundColor": "red",
    "createdAt": "2024-10-17T15:31:01.256068",
    "updatedAt": "2024-10-17T15:31:01.256068",
    "boardLists": []
  }
}
```
***  

### 보드 수정
```http
PUT  http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
| `Body`     | `String` | title | 
|`Body`     | `String`| backgroundColor|

#### Request Example
  ```http
PUT http://localhost:8080/workspaces/2/boards/1
   {
      "title": "editedTitle",
      "backgroundColor": "blue"
    }
  ```

#### Response Example
```http
{
  "code": 200,
  "message": "보드 수정에 성공하였습니다.",
  "data": {
    "id": 1,
    "title": "editedTitle",
    "backgroundColor": "blue",
    "createdAt": "2024-10-17T15:31:01.256068",
    "updatedAt": "2024-10-17T15:31:01.256068"
  }
}
```
***
### 보드 삭제
```http
DELETE  http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|

#### Request Example
  ```http
DELETE http://localhost:8080/workspaces/2/boards/2
  ```

#### Response Example
```http
{
  "code": 201,
  "message": "보드 삭제에 성공하였습니다.",
  "data": {}
}
```
***

## List
### 리스트 생성
```http
POST http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|


#### Request Example
```http
POST http://localhost:8080/workspaces/2/boards/1/lists
{
  "title": "list1"
}
```
#### Response Example
```http
{
  "code": 201,
  "message": "리스트가 생성되었습니다",
  "data": {
    "listId": 1,
    "boardId": 1,
    "title": "list1",
    "order": 1
  }
}
```
***      

### 리스트 수정
```http
PUT http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Body`     | `String`| title|
|`Body`     | `String`| orderNum|


#### Request Example
```http
PUT http://localhost:8080/workspaces/2/boards/1/lists/1
{
  {
  "title": "editlist1",
  "orderNum": 1
}
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "리스트가 수정되었습니다",
  "data": {
    "listId": 1,
    "boardId": 1,
    "title": "editlist1",
    "order": 1
  }
}
```
***  

### 리스트 삭제
```http
DELETE http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|


#### Request Example
```http
DELETE http://localhost:8080/workspaces/2/boards/1/lists/2
```
#### Response Example
```http
{
  "code": 200,
  "message": "리스트가 삭제되었습니다",
  "data": null
}
```
***  

## Card

### 카드 생성
```http
POST http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Body`     | `String`| title|
|`Body`     | `String`| content|


#### Request Example
```http
POST http://localhost:8080/workspaces/2/boards/1/lists/1/cards
{
  "title": "cardTitle1",
  "content": "cardContent1"
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "카드가 등록되었습니다.",
  "data": {
    "id": 1,
    "title": "cardTitle1",
    "content": "cardContent1"
  }
}
```
***

### 카드 단건 조회
```http
GET http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/{cardId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Path`     | `Integer`| cardId|



#### Request Example
```http
GET http://localhost:8080/workspaces/2/boards/1/lists/1/cards/1
```
#### Response Example
```http
{
  "code": 200,
  "message": "카드를 불러왔습니다.",
  "data": {
    "id": 1,
    "title": "cardTitle1",
    "content": "cardContent1",
    "createdAt": "2024-10-17T17:03:28.57465",
    "updatedAt": "2024-10-17T17:03:28.57465",
    "members": [
      {
        "id": 5,
        "email": "a3@gmail.com"
      }
    ],
    "comments": []
  }
}
```
***  

### 카드 수정
```http
PUT http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/{cardId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Path`     | `Integer`| cardId|
|`Body`     | `String`| title|
|`Body`     | `String`| content|



#### Request Example
```http
PUT http://localhost:8080/workspaces/2/boards/1/lists/1/cards/1
{
  "title": "cardEditTitle",
  "content": "CardEditContent"
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "카드 수정이 완료 됐습니다.",
  "data": {
    "id": 1,
    "title": "cardEditTitle",
    "content": "CardEditContent"
  }
}
```
***  

### 카드 삭제
```http
DELETE http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/{cardId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Path`     | `Integer`| cardId|




#### Request Example
```http
DELETE http://localhost:8080/workspaces/2/boards/1/lists/1/cards/2
{
  "title": "cardEditTitle",
  "content": "CardEditContent"
}

```
#### Response Example
```http
{
  "code": 200,
  "message": " 카드가 삭제되었습니다",
  "data": {
    "id": 2
  }
}
```
***  

## 카드 멤버

### 카드 멤버 추가
```http
POST http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/{cardId}/member
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Body`     | `String`| memberId|



#### Request Example
```http
POST http://localhost:8080/workspaces/2/boards/1/lists/1/cards/1/member
{
  "memberId": [
    1
  ]
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "카드 담당자 등록 성공.",
  "data": {
    "member": [
      {
        "id": 1,
        "email": "ad32@gmail.com"
      }
    ]
  }
}
```
***

### 카드 멤버 삭제
```http
DELETE http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/{cardId}/member
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Body`     | `String`| memberId|



#### Request Example
```http
DELETE http://localhost:8080/workspaces/2/boards/1/lists/1/cards/1/member
{
  "memberId": [
    1
  ]
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "카드 담당자 삭제 성공",
  "data": {
    "member": [
      {
        "id": 1,
        "email": "ad32@gmail.com"
      }
    ]
  }
}
```
***

### 카드 검색
```http
GET http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/search
```

#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Body`     | `Long`| cardId|
|`Body`     | `Long`| boardId|
|`Body`     | `String`| title|
|`Body`     | `String`| content|
|`Body`     | `LocalDateTime`| startAt|
|`Body`     | `LocalDateTime`| endAt|

(* Body는 필수 입력 값 아님)


#### Request Example
```http
GET http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/search
{
    "cardId" : 1,
    "boardId" : 1,
    "title" : "title",
    "content" : "content",
    "startAt" : "2024-10-01T00:00:00",
    "endAt" : "2024-10-30T00:00:00"
}
```

#### Response Example

```http
{
    "code": 200,
    "message": "카드들이 검색되었습니다",
    "data": {
        "content": [
            {
                "cardId": 1,
                "boardId": 1,
                "title": "title1",
                "content": "content1",
                "createdAt": "2024-10-18T10:10:19.837049",
                "updatedAt": "2024-10-18T10:10:19.837049"
            }
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 10,
            "sort": {
                "empty": true,
                "unsorted": true,
                "sorted": false
            },
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalPages": 1,
        "totalElements": 1,
        "first": true,
        "size": 10,
        "number": 0,
        "sort": {
            "empty": true,
            "unsorted": true,
            "sorted": false
        },
        "numberOfElements": 1,
        "empty": false
    }
}

```
***

### 카드 조회수 랭킹 조회
```http
GET http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/ranking
```

#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|

#### Request Example
```http
GET http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/ranking
```

### Response Example

```http
{
    "code": 200,
    "message": "카드를 불러왔습니다.",
    "data": [
        {
            "rank": 1,
            "cardId": 1
        },
        {
            "rank": 2,
            "cardId": 3
        },
        {
            "rank": 3,
            "cardId": 2
        }
    ]
}

```

***



### Comment

### 댓글 생성
```http
POST http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/{cardsId}/comments
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Path`     | `Integer`| cardId|
|`Body`     | `String`| title|
|`Body`     | `String`| content|


#### Request Example
```http
POST http://localhost:8080/workspaces/2/boards/2/lists/1/cards/1/comments
{
  "title": "comment1",
  "content": "content1"
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "댓글 등록 성공",
  "data": {
    "id": 1,
    "card_id": 1,
    "comment": "content1"
  }
}
```
***

### 댓글 수정
```http
PUT http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/{cardsId}/comments/{commentId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Path`     | `Integer`| cardId|
|`Path`     | `Integer`| commentId|
|`Body`     | `String`| comment|


#### Request Example
```http
POST http://localhost:8080/workspaces/2/boards/2/lists/1/cards/1/comments/1
{
  "comment": "editComment"
}
```
#### Response Example
```http
{
  "code": 200,
  "message": "댓글 수정 성공",
  "data": {
    "id": 1,
    "card_id": 1,
    "comment": "editComment"
  }
}
```
***

### 댓글 삭제
```http
DELETE http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}/lists/{listId}/cards/{cardsId}/comments/{commentId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Path`     | `Integer`| boardId|
|`Path`     | `Integer`| listId|
|`Path`     | `Integer`| cardId|
|`Path`     | `Integer`| commentId|



#### Request Example
```http
DELETE http://localhost:8080/workspaces/2/boards/2/lists/1/cards/1/comments/1
```
#### Response Example
```http
{
  "code": 200,
  "message": "댓글 삭제 성공",
  "data": {
    "id": 1
  }
}
```
***

## File Upload
### 사진 업로드
```http
POST http://localhost:8080/api/cards/{card_id}/files
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
|`Path`     | `Integer`| cardId|
|`Param`     | `key`| files|



#### Request Example
```http
POST http://localhost:8080/api/cards/2/files
Key(files) : Value(filename.jpg)
```
#### Response Example
```http
{
"code": 200,
"message": "파일 작업 요청에 성공 하였습니다",
  "data": [
  "${awsS3server}filename.jpg",
  ]
}
```
***

### 사진 조회
```http
GET http://localhost:8080/api/cards/{card_id}/files
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
|`Path`     | `Integer`| cardId|
|`Param`     | `key`| files|



#### Request Example
```http
GET http://localhost:8080/api/cards/2/files
form-data
Key(files) : Value(filename.jpg)
```
#### Response Example
```http
{
"code": 200,
"message": "파일 작업 요청에 성공 하였습니다",
  "data": [
  "filename.jpg",
  ]
}
```
***

### 사진 삭제
```http
DELETE http://localhost:8080/api/cards/{card_id}/files
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
|`Path`     | `Integer`| cardId|
|`Param`     | `key`| files|



#### Request Example
```http
DELETE http://localhost:8080/api/cards/2/files
Key(files) : Value(filename.jpg)
```
#### Response Example
```http
{
"code": 200,
"message": "파일 작업 요청에 성공 하였습니다",
  "data": [
  "filename.jpg",
  ]
}
```
***


#  📊 SQL

    create table users
    (
        id         bigint auto_increment
            primary key,
        created_at datetime(6)            null,
        updated_at datetime(6)            null,
        deleted    bit                    null,
        email      varchar(255)           not null,
        password   varchar(255)           not null,
        role       enum ('ADMIN', 'USER') not null,
        constraint UK6dotkott2kjsp8vw4d0m25fb7
            unique (email)
    );
    
    create table workspace
    (
        id          bigint auto_increment
            primary key,
        created_at  datetime(6)  null,
        updated_at  datetime(6)  null,
        description varchar(300) not null,
        title       varchar(100) not null
    );
    
    create table board
    (
        id               bigint auto_increment
            primary key,
        created_at       datetime(6)  null,
        updated_at       datetime(6)  null,
        background_color varchar(7)   not null,
        title            varchar(100) not null,
        workspace_id     bigint       not null,
        constraint FKh8r4ryxrng25r7ko3yh5eaudu
            foreign key (workspace_id) references workspace (id)
    );
    
    create table board_list
    (
        id         bigint auto_increment
            primary key,
        created_at datetime(6)  null,
        updated_at datetime(6)  null,
        order_num  int          null,
        title      varchar(255) not null,
        board_id   bigint       not null,
        user_id    bigint       not null,
        constraint UKqraqqtg7ec9n4hgg2og4br8p2
            unique (title),
        constraint FKhr9yvsrsbk1gp346h44jsovv6
            foreign key (board_id) references board (id),
        constraint FKmhemj28ukt33oao8xbtcn2ndt
            foreign key (user_id) references users (id)
    );
    
    create table card
    (
        id            bigint auto_increment
            primary key,
        created_at    datetime(6)  null,
        updated_at    datetime(6)  null,
        content       varchar(255) null,
        title         varchar(255) null,
        board_list_id bigint       null,
        user_id       bigint       null,
        constraint FK8ah8qm5rxxlf22ekmq9v9u5fa
            foreign key (board_list_id) references board_list (id),
        constraint FKq5apcc4ddrab8t48q2uqvyquq
            foreign key (user_id) references users (id)
    );
    
    create table attachment
    (
        id               bigint auto_increment
            primary key,
        created_at       datetime(6)  null,
        updated_at       datetime(6)  null,
        origin_file_name varchar(255) null,
        path             varchar(255) null,
        card_id          bigint       null,
        user_id          bigint       null,
        constraint FKbj8rm4iort67j9jp8ibdftkmq
            foreign key (user_id) references users (id),
        constraint FKpyjq6uiperx43dbsny1gjvxne
            foreign key (card_id) references card (id)
    );
    
    create table card_log
    (
        id           bigint auto_increment
            primary key,
        created_at   datetime(6)            null,
        updated_at   datetime(6)            null,
        member_email varchar(255)           null,
        status       enum ('ADD', 'DELETE') not null,
        card_id      bigint                 null,
        user_id      bigint                 null,
        constraint FK5w4bh9xv3xicbwrf9cvmhfgfu
            foreign key (card_id) references card (id),
        constraint FKi2rwehm0vb01xvhqkl5e41vwu
            foreign key (user_id) references users (id)
    );
    
    create table card_member
    (
        id         bigint auto_increment
            primary key,
        created_at datetime(6) null,
        updated_at datetime(6) null,
        card_id    bigint      null,
        user_id    bigint      null,
        constraint FKcoy0y9394gd8yp0nq0yuck2q1
            foreign key (user_id) references users (id),
        constraint FKgp6lai9ewkcfodigcua5taanf
            foreign key (card_id) references card (id)
    );
    
    create table comment
    (
        id         bigint auto_increment
            primary key,
        created_at datetime(6)  null,
        updated_at datetime(6)  null,
        comment    varchar(255) null,
        card_id    bigint       null,
        user_id    bigint       null,
        constraint FKqgv5aujiclf0iihwxf4gmkf18
            foreign key (card_id) references card (id),
        constraint FKqm52p1v3o13hy268he0wcngr5
            foreign key (user_id) references users (id)
    );
    
    create table member
    (
        id           bigint auto_increment
            primary key,
        created_at   datetime(6)                                           null,
        updated_at   datetime(6)                                           null,
        member_role  enum ('BOARD_MEMBER', 'READ_ONLY', 'WORKSPACE_ADMIN') not null,
        user_id      bigint                                                not null,
        workspace_id bigint                                                not null,
        constraint FKe6yo8tn29so0kdd1mw4qk8tgh
            foreign key (user_id) references users (id),
        constraint FKnhqfvlg5wv3c3qok7st4cuvii
            foreign key (workspace_id) references workspace (id)
    );



