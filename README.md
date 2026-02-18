# Secure Mini Payment API

A secure backend payment simulation system built using Spring Boot.

This project was developed as part of a security-focused backend design exercise.  
The goal was to design a simplified payment system while implementing core security controls such as authentication, input validation, duplicate payment prevention, and transaction ownership enforcement.

---

## 🚀 Features

- User Registration (Secure password hashing)
- User Login with JWT Authentication
- Token expiration handling
- Basic login rate limiting
- Secure payment processing
- Duplicate payment prevention
- Transaction history (user-specific)
- Security logging
- Modern UI dashboard

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Spring Data JPA
- H2 / MySQL (configurable)
- HTML, CSS, JavaScript (Frontend)

---

## 🏗 System Design Approach

The backend follows a layered architecture:

Controller → Service → Repository → Database  
                        ↓  
                    Security Layer

### Why this structure?

- Separation of concerns
- Easier debugging and testing
- Prevents business logic inside controllers
- Cleaner security enforcement

All endpoints are protected except:
- `/api/register`
- `/api/login`

JWT tokens are required for all payment-related operations.

---

## 🔐 Security Considerations

While designing this system, I considered the following possible attacks:

### 1. Password Theft
Passwords are hashed using BCrypt before storing in the database.  
Plain text storage is strictly avoided.

### 2. Brute Force Login
Basic in-memory login rate limiting is implemented to block repeated failed attempts.

### 3. Token Abuse
JWT tokens have expiration time.  
Invalid or expired tokens are rejected.

### 4. Duplicate Payment Submission
Each payment requires a unique `referenceId`.  
Database-level unique constraint prevents replay or double-click payment attacks.

### 5. Horizontal Privilege Escalation
Users can only access their own transactions.  
Transaction queries are filtered using authenticated user identity.

### 6. Input Validation
All incoming requests use validation annotations to prevent malformed input.

### 7. Suspicious Activity Logging
The system logs:
- Failed login attempts
- Duplicate registration attempts
- High-value transaction attempts
- Duplicate payment attempts

---

## 💳 API Endpoints

### Register
POST `/api/register`

```json
{
  "email": "user@example.com",
  "password": "secure123"
}
