# JWT Authentication Project

A Spring Boot backend project implementing JWT-based authentication and authorization using Spring Security, MySQL, and Refresh Token mechanism.

---

# Features

- User Registration (Signup)
- User Login (Signin)
- JWT Authentication
- Refresh Token Authentication
- Role-Based Authorization
- Password Encryption using BCrypt
- Change Password API
- Update Profile API
- Global Exception Handling
- MySQL Database Integration
- Spring Security Integration

---

# Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- MySQL
- Maven
- Hibernate

---

# Authentication Flow

## 1. User Login

User sends username and password to:

```http
POST /api/auth/signin
```

If credentials are valid:
- JWT Access Token is generated
- Refresh Token is generated and stored in database

---

## 2. Access Protected APIs

Frontend sends JWT token in Authorization header:

```http
Authorization: Bearer <token>
```

Spring Security validates token before allowing access.

---

## 3. Refresh Token Flow

When access token expires:
- Frontend sends refresh token
- Backend verifies refresh token
- New access token is generated

Endpoint:

```http
POST /api/auth/refreshtoken
```

---

# APIs Implemented

## Authentication APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/auth/signup | Register new user |
| POST | /api/auth/signin | Login user |
| POST | /api/auth/refreshtoken | Generate new access token |

---

## User APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/user/test | User role access |
| PUT | /api/user/update | Update user profile |
| POST | /api/user/change-password | Change password |

---

## Admin APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/admin/test | Admin role access |

---

# Security Features

## BCrypt Password Encryption

Passwords are securely stored using BCrypt hashing.

Example:

```java
passwordEncoder.encode(password)
```

---

## Role-Based Authorization

Implemented using:

```java
@PreAuthorize("hasRole('USER')")
```

and Spring Security configuration.

---

# Exception Handling

Implemented centralized exception handling using:

```java
@RestControllerAdvice
```

Custom Exception:

```java
ResourceNotFoundException
```

---

# Database

MySQL database used with Hibernate JPA.

Main entities:
- User
- Role
- RefreshToken

Relationships:
- Many-to-Many → User & Role
- Many-to-One → RefreshToken & User

---

# Project Structure

```text
src/main/java/com/bezkoder/springjwt
│
├── controllers
├── models
├── repository
├── security
├── services
├── payload
├── exception
└── config
```

---

# Future Improvements

- Email Verification
- Forgot Password Flow
- Logout with Token Blacklisting
- Docker Deployment
- Swagger Documentation
- Unit Testing

---

# Author

Bilwesh
