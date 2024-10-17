![header](https://capsule-render.vercel.app/api?type=waving&height=300&color=gradient&text=Trello)
- 한 줄 정리 : 
- 내용 :

# 🚀 STACK 
**Environment**

![인텔리제이](   https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![깃허브](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)
![깃](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![POSTMAN](https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white)

**Development**

![자바](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![SPRING BOOT](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![SQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

# 🤔 Authors

- [@gyun97](https://github.com/gyun97)
- [@kkm4512](https://github.com/kkm4512)
- [@LJH4987](https://github.com/LJH4987)
- [@ican0422](https://github.com/ican0422)
- [@tae98](https://www.github.com/tae98)

# 🙏 Acknowledgements

 - [Awesome Readme Templates](https://awesomeopensource.com/project/elangosundar/awesome-README-templates)
 - [Awesome README](https://github.com/matiassingers/awesome-readme)
 - [How to write a Good readme](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)

# 🖼️ Wireframe
### [📎FigmaLink](https://www.figma.com/design/tfRF6u9aGluYYLKrnDBEoL/Untitled?node-id=0-1&node-type=canvas&t=Epr6VQubsSxpxFCQ-0)
![Untitled (1)](https://github.com/user-attachments/assets/450ff745-9b4a-4b28-861c-5320dc6b7cce)

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
  "password": "1234!abc",
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
  "password": "1234!abc",
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
PUT http://localhost:8080/{workspacesId}
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 
|`Body`     | `String`| title|
|`Body`     | `String`| description|


#### Request Example
```http
PUT http://localhost:8080/1
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
  "userId": 2
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
GET http://localhost:8080/workspaces/{workspaceId}/boards
```
#### Request Field 
| Parameter | Type     |Description                 |
| :-------- | :------- | :------------------------- |    
| `Path`     | `Integer` | workspaceId | 


#### Request Example
```http
GET http://localhost:8080/workspaces/2/boards
{
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
GET http://localhost:8080/workspaces/{workspaceId}/boards/{boardId}
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

# ⚒️ ERD Diagram

#  📊 SQL

