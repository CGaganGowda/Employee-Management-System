# Employee Management System

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-brightgreen?style=flat-square&logo=springsecurity)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=flat-square&logo=postgresql)
![Swagger](https://img.shields.io/badge/Docs-Swagger%20UI-85EA2D?style=flat-square&logo=swagger)
![Build](https://img.shields.io/badge/Build-Maven-red?style=flat-square&logo=apachemaven)
![License](https://img.shields.io/badge/License-Apache%201.0-blue?style=flat-square)

A **RESTful Employee Management API** built with Spring Boot 4 and Java 21. The system exposes CRUD endpoints for employee records, secured by **JWT-based authentication** and **role-based access control** (`admin`, `manager`, `employee`). It follows a clean layered architecture — Controller → Service → Repository — with DTOs, Bean Validation, global exception handling, and interactive API docs via Swagger UI.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Reference](#api-reference)
- [Authentication Flow](#authentication-flow)
- [Exception Handling](#exception-handling)
- [Actuator and Monitoring](#actuator-and-monitoring)
- [Known Issues and Improvements](#known-issues-and-improvements)

---

## Features

- **Employee CRUD** — create, retrieve, update, and delete employee records
- **JWT Authentication** — stateless token-based auth using `jjwt`; tokens expire after 7 days (604800000 ms)
- **Role-based access control** via `@PreAuthorize` — three roles: `admin`, `manager`, `employee`
- **DTO pattern** — `EmployeeDto`, `UserDto`, `LoginDto`, `JwtAuthResponse` decouple the API layer from JPA entities
- **ModelMapper** — automatic entity ↔ DTO conversion configured as a Spring bean in `EmployeeApplication`
- **Bean Validation** — `@NotEmpty`, `@Email`, `@Min`, `@Positive`, `@Size` constraints on `EmployeeDto`
- **Global exception handling** — `@RestControllerAdvice` covers `ResourceNotFoundException`, `EmailAlreadyExistsException`, `NameAlreadyExistsException`, `APIException`, `@Valid` validation errors, and a generic catch-all
- **Swagger UI** — auto-generated interactive API docs via Springdoc OpenAPI 3, configured in `EmployeeApplication`
- **Spring Actuator** — health, info, metrics, and shutdown endpoints fully exposed
- **BCrypt** password encoding via `BCryptPasswordEncoder` bean in `SpringSecurityConfig`

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.0 |
| Security | Spring Security + JWT (`jjwt 0.13.0`) |
| Persistence | Spring Data JPA + Hibernate |
| Database | PostgreSQL |
| DTO Mapping | ModelMapper 3.1.1 |
| Validation | Spring Boot Validation Starter (`jakarta.validation`) |
| API Docs | Springdoc OpenAPI 3 / Swagger UI |
| Monitoring | Spring Boot Actuator |
| Build | Maven (Maven Wrapper included) |
| Utilities | Lombok |

---

## Project Structure

    src/
    └── main/
        └── java/com/manage/employee/
            ├── EmployeeApplication.java             # Entry point; ModelMapper bean; OpenAPI definition
            ├── config/
            │   └── SpringSecurityConfig.java        # Security filter chain, JWT filter wiring, BCrypt bean
            ├── controller/
            │   ├── AuthController.java              # POST api/auth/register, POST api/auth/login
            │   └── EmployeeController.java          # CRUD endpoints under api/employees
            ├── dto/
            │   ├── EmployeeDto.java                 # Employee request/response DTO with validation
            │   ├── UserDto.java                     # User registration DTO
            │   ├── LoginDto.java                    # Login request DTO (usernameOrEmail + password)
            │   └── JwtAuthResponse.java             # Login response DTO (accessToken + tokenType)
            ├── exception/
            │   ├── GlobalExceptionHandler.java      # @RestControllerAdvice — all exception mappings
            │   ├── ErrorDetails.java                # Error response body (timestamp, message, path, errorCode)
            │   ├── ResourceNotFoundException.java   # 404 — thrown when employee/user not found by ID
            │   ├── EmailAlreadyExistsException.java # 400 — thrown on duplicate employee email
            │   ├── NameAlreadyExistsException.java  # 400 — thrown on duplicate employee name
            │   └── ApiException.java                # 400 — general API-level exception
            ├── mapper/
            │   └── MapperClass.java                 # Manual entity ↔ DTO mapper (superseded by ModelMapper)
            ├── model/
            │   ├── Employee.java                    # Employee entity: id, name, salary, dept, email
            │   ├── User.java                        # User entity with ManyToMany → Role
            │   └── Role.java                        # Role entity: id, name
            ├── repo/
            │   ├── EmployeeRepo.java                # JpaRepository + findByEmail(String)
            │   ├── UserRepo.java                    # JpaRepository + findByUsernameOrEmail, existsBy checks
            │   └── RoleRepo.java                    # JpaRepository for Role
            ├── security/
            │   ├── JwtTokenProvider.java            # Token generation, username extraction, validation
            │   ├── JwtAuthenticationFilter.java     # OncePerRequestFilter — validates Bearer token per request
            │   ├── JwtAuthenticationEntryPoint.java # Returns 401 for unauthenticated requests
            │   └── CustomUserDetailsService.java    # Loads user by username or email from DB
            ├── service/
            │   ├── AuthService.java                 # register() and login() interface
            │   ├── AuthServiceImpl.java             # Duplicate checks, BCrypt encode, JWT generation
            │   ├── EmployeeService.java             # CRUD interface
            │   └── EmployeeServiceImpl.java         # CRUD impl: ModelMapper + duplicate email guard
            └── utils/
                └── PasswordEncoderImpl.java         # Utility to generate BCrypt hashes offline
    └── resources/
        └── application.properties
    └── test/
        └── java/com/manage/employee/
            └── EmployeeApplicationTests.java        # Context load test (placeholder)

---

## Getting Started

### Prerequisites

- Java 21+
- PostgreSQL 14+
- Maven 3.9+ (or use the included `./mvnw` wrapper)

### 1. Clone the repository

    git clone https://github.com/CGaganGowda/Employee-Management-System.git
    cd Employee-Management-System

### 2. Set up the database

Create a PostgreSQL database named `employee-dto`:

    CREATE DATABASE "employee-dto";

Then seed the `roles` table. The application expects these records to exist before any user registers:

    INSERT INTO roles (name) VALUES ('ROLE_USER');
    INSERT INTO roles (name) VALUES ('ROLE_admin');
    INSERT INTO roles (name) VALUES ('ROLE_manager');
    INSERT INTO roles (name) VALUES ('ROLE_employee');

> Hibernate will auto-create all tables on first run because `spring.jpa.hibernate.ddl-auto=update` is set.

### 3. Configure application.properties

Update `src/main/resources/application.properties` with your local values:

    spring.datasource.url=jdbc:postgresql://localhost:5432/employee-dto
    spring.datasource.username=YOUR_DB_USERNAME
    spring.datasource.password=YOUR_DB_PASSWORD

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

    app.jwt-secret=YOUR_BASE64_ENCODED_SECRET
    app.jwt-expiration-milliseconds=604800000

    management.endpoints.web.exposure.include=*
    management.endpoint.health.show-details=always
    management.endpoint.shutdown.enabled=true
    info.app.name=Spring Boot Restful Web Services
    info.app.description=Spring Boot Restful Web Services Demo
    info.app.version=1.0.0

> **Security note:** Never commit real credentials or your JWT secret. Use environment variables in production.

### 4. Run the application

    ./mvnw spring-boot:run

Server starts at `http://localhost:8080`.

---

## API Reference

### Auth Endpoints — `api/auth`

No authentication required.

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `api/auth/register` | Register a new user |
| `POST` | `api/auth/login` | Login and receive a JWT token |

**Register — request body (UserDto):**

    {
      "name": "Gagan Gowda",
      "username": "gagan",
      "email": "gagan@example.com",
      "password": "secret123"
    }

**Login — request body (LoginDto):**

    {
      "usernameOrEmail": "gagan",
      "password": "secret123"
    }

**Login — response (JwtAuthResponse):**

    {
      "accessToken": "<jwt_token>",
      "tokenType": "Bearer"
    }

---

### Employee Endpoints — `api/employees`

All endpoints require a valid JWT in the `Authorization` header:

    Authorization: Bearer <your_token>

| Method | Endpoint | Description | Allowed Roles |
|--------|----------|-------------|---------------|
| `POST` | `api/employees/create` | Create a new employee | `admin`, `manager` |
| `GET` | `api/employees` | Get all employees | `admin`, `manager`, `employee` |
| `GET` | `api/employees/get/{id}` | Get employee by ID | `admin`, `manager`, `employee` |
| `PUT` | `api/employees/update/{id}` | Update an employee | `admin`, `manager` |
| `DELETE` | `api/employees/delete/{id}` | Delete an employee | `admin`, `manager` |

**Employee request/response body (EmployeeDto):**

    {
      "id": 1,
      "name": "Jane Smith",
      "salary": 75000,
      "dept": "Engineering",
      "email": "jane.smith@example.com"
    }

**Validation rules on EmployeeDto:**

| Field | Constraints |
|-------|-------------|
| `name` | Not empty, 1–30 characters |
| `salary` | Not null, minimum 1, must be positive |
| `dept` | Not empty |
| `email` | Not empty, valid email format |

---

## Authentication Flow

    1. POST api/auth/register
       └─ Checks for duplicate username and email (throws APIException if found)
       └─ BCrypt-encodes password
       └─ Assigns ROLE_USER from the roles table
       └─ Saves and returns the User entity

    2. POST api/auth/login
       └─ AuthenticationManager authenticates via CustomUserDetailsService
          └─ Looks up user by username OR email from the users table
          └─ Loads roles as GrantedAuthority set
       └─ JwtTokenProvider generates a signed token (subject = username, expiry = 7 days)
       └─ Returns JwtAuthResponse { accessToken, tokenType: "Bearer" }

    3. Secured requests
       └─ JwtAuthenticationFilter (OncePerRequestFilter) extracts token from Authorization header
       └─ JwtTokenProvider.validateToken() verifies the signature
       └─ JwtTokenProvider.getUsernameFromToken() extracts the subject
       └─ UserDetailsService loads user from DB and populates SecurityContext
       └─ @PreAuthorize checks the role before the controller method executes

---

## Exception Handling

All errors return an ErrorDetails response body:

    {
      "timestamp": "2025-01-01T10:00:00",
      "message": "Employee not found in id : '99'",
      "path": "uri=/api/employees/get/99",
      "errorCode": "USER_NOT_FOUND"
    }

| Exception | HTTP Status | Error Code |
|-----------|-------------|------------|
| `ResourceNotFoundException` | `404 Not Found` | `USER_NOT_FOUND` |
| `EmailAlreadyExistsException` | `400 Bad Request` | `EMAIL_ALREADY_EXISTS` |
| `NameAlreadyExistsException` | `400 Bad Request` | `EMAIL_ALREADY_EXISTS` |
| `APIException` | `400 Bad Request` | `API_EXCEPTION` |
| `@Valid` failure | `400 Bad Request` | Field-name → message map |
| Any other `Exception` | `500 Internal Server Error` | `INTERNAL_SERVER_ERROR` |

---

## Actuator and Monitoring

| Endpoint | Description |
|----------|-------------|
| `GET /actuator/health` | Application health with full component details |
| `GET /actuator/info` | App name, description, and version from `info.*` properties |
| `GET /actuator/metrics` | JVM, HTTP, and system metrics |
| `POST /actuator/shutdown` | Graceful shutdown — **restrict this in production** |

---

## API Documentation (Swagger UI)

Once the application is running, open:

    http://localhost:8080/swagger-ui.html

The OpenAPI definition in `EmployeeApplication.java` sets the title (`Employee Management System REST API`), version (`v1.0`), contact (`gagancgowda971@gmail.com`), and Apache 1.0 license.

---

## License

Licensed under the [Apache License 1.0](https://www.apache.org/licenses/LICENSE-1.0).
