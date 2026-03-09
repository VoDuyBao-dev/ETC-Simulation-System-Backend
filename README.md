# 🚧 ETC System – Electronic Toll Collection Simulation

Hệ thống mô phỏng **Thu phí tự động không dừng (ETC – Electronic Toll Collection)** được xây dựng nhằm nghiên cứu và minh họa cách vận hành của hệ thống thu phí thông minh trong giao thông.

Dự án bao gồm 3 thành phần chính:

```
ETC System
│
├── backend (Spring Boot API Server)
│
├── frontend-admin (ReactJS Web Dashboard)
│
└── frontend-user (ReactJS Mobile Interface)
```

- **Backend:** Java Spring Boot (REST API)
- **Frontend Admin:** ReactJS (quản trị hệ thống)
- **Frontend User:** ReactJS (mobile UI cho người dùng)

---

# 🏗️ System Architecture

```
                +----------------------+
                |      React Web       |
                |       (Admin)        |
                +----------+-----------+
                           |
                           | REST API
                           |
+--------------------------v---------------------------+
|                Spring Boot Backend                   |
|                                                      |
|  - Authentication / Authorization                    |
|  - User Management                                   |
|  - Vehicle Management                                |
|  - Toll Station Management                           |
|  - Transaction Processing                            |
|  - Payment Simulation                                |
|                                                      |
+--------------------------+---------------------------+
                           |
                           |
                           v
                   +---------------+
                   |   MySQL DB    |
                   +---------------+
                           ^
                           |
                           |
                +----------+-----------+
                |      React Mobile    |
                |      (User App)      |
                +----------------------+
```

---

# 📦 Tech Stack

### Backend
- Java 21
- Spring Boot **3.5.0**
- Maven
- MapStruct
- MySQL
- Spring Security
- JWT Authentication

### Frontend
- ReactJS
- NodeJS
- npm / yarn
- React Router
- Tailwind / CSS

### Development Tools

- IntelliJ IDEA
- VSCode
- Git
- Postman
- MySQL Workbench

---

# 📋 Prerequisites (Yêu cầu môi trường)

Cần cài đặt các công cụ sau trước khi chạy dự án.

## 1️⃣ Java Development Kit

Cài đặt **Java 21**

Kiểm tra:

```bash
java -version
```

Kết quả mong muốn:

```
java version "21"
```

---

## 2️⃣ Maven

Kiểm tra Maven:

```bash
mvn -version
```

Nếu chưa có:

```
https://maven.apache.org/download.cgi
```

---

## 3️⃣ NodeJS

Cài NodeJS:

```
https://nodejs.org
```

Kiểm tra:

```bash
node -v
npm -v
```

---

## 4️⃣ MySQL

Cài đặt MySQL:

```
https://dev.mysql.com/downloads/mysql/
```

Tạo database:

```sql
CREATE DATABASE etc_system;
```

---

# 🔐 IMPORTANT – Secret Configuration

⚠️ **Dự án yêu cầu một file secret để chạy backend.**

File này **KHÔNG được push lên Git**.

Ví dụ:

```
application-secret.yml
```

Hoặc:

```
.env
```

File này chứa:

- Database password
- JWT Secret Key
- API Keys

📌 Thành viên trong nhóm cần **liên hệ admin dự án để lấy file này.**

Sau khi nhận file:

```
backend/src/main/resources/
```

---

# ⚙️ Backend Setup (Spring Boot)

Thư mục backend:

```
backend/
```

## 1️⃣ Cài dependencies

```bash
mvn clean install
```

---

## 2️⃣ Cấu hình database

Mở file:

```
application.yml
```

hoặc

```
application-secret.yml
```

Cập nhật:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/etc_system
    username: root
    password: your_password
```

---

## 3️⃣ Run Backend

### IntelliJ

1. Open project
2. Chọn file:

```
ETCSystemApplication.java
```

3. Run

---

### VSCode

```bash
cd backend
mvn spring-boot:run
```

---

## 4️⃣ Kiểm tra server

```
http://localhost:8080
```

API example:

```
http://localhost:8080/api
```

---

# 🌐 Frontend Setup (ReactJS)

Có **2 frontend**

```
frontend-admin
frontend-user
```

---

# 🖥️ Admin Web (React)

Thư mục:

```
frontend-admin/
```

## 1️⃣ Install dependencies

```bash
npm install
```

---

## 2️⃣ Config API URL

Mở file:

```
src/config/api.js
```

Sửa:

```javascript
export const API_URL = "http://localhost:8080/api"
```

---

## 3️⃣ Run Web

```bash
npm start
```

Server chạy:

```
http://localhost:3000
```

---

# 📱 User Mobile (React)

Thư mục:

```
frontend-user/
```

## 1️⃣ Install dependencies

```bash
npm install
```

---

## 2️⃣ Config API

```
src/config/api.js
```

```javascript
export const API_URL = "http://localhost:8080/api"
```

---

## 3️⃣ Run App

```bash
npm start
```

---

# ▶️ Running Full System

Chạy theo thứ tự:

### 1️⃣ Start Database

```
MySQL
```

---

### 2️⃣ Start Backend

```bash
cd backend
mvn spring-boot:run
```

Server:

```
http://localhost:8080
```

---

### 3️⃣ Start Admin Web

```bash
cd frontend-admin
npm start
```

```
http://localhost:3000
```

---

### 4️⃣ Start User Mobile

```bash
cd frontend-user
npm start
```

---

# 🔑 Default Accounts

## Admin

```
email: admin@example.com
password: admin123
```

---

## User

```
email: user@example.com
password: user123
```

---

# 📂 Project Structure

```
ETCSystem
│
├── backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   └── config
│
├── frontend-admin
│   ├── src
│   ├── components
│   ├── pages
│   └── services
│
└── frontend-user
    ├── src
    ├── components
    ├── pages
    └── services
```

---

# 🧪 Testing API

Có thể test bằng:

### Postman

Example:

```
POST /api/auth/login
```

```
GET /api/vehicles
```

```
GET /api/transactions
```

---

# 🛠️ Development Tools

IDE được khuyến nghị:

### IntelliJ IDEA

Dùng cho:

- Spring Boot
- Backend

---

### VSCode

Dùng cho:

- ReactJS
- API testing

Extensions gợi ý:

```
ESLint
Prettier
Spring Boot Extension Pack
REST Client
```

---

# ⚠️ Troubleshooting

## 1️⃣ Backend không connect database

Kiểm tra:

```
application-secret.yml
```

---

## 2️⃣ Frontend không gọi được API

Kiểm tra:

```
API_URL
```

---

## 3️⃣ Port conflict

Đổi port trong:

```
application.yml
```

```
server.port=8081
```

---

# 👥 Development Team

ETC Simulation System

- Backend Developers
- Frontend Developers
- System Design

---

# 📄 License

Educational Project – Intelligent Transportation Systems