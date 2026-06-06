# JobPortal Backend

<div align="center">

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-API_Docs-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

**A production-ready RESTful API powering a full-featured Job Portal — built with Spring Boot 4, secured with JWT, and designed for both job seekers and recruiters.**

[Live API Docs](#api-documentation) • [Features](#features) • [Setup](#getting-started) • [Architecture](#architecture)

</div>

---

## Overview

JobPortal Backend is a robust REST API that serves as the backbone of a job portal platform. It handles user authentication with role-based access control (Job Seeker vs Recruiter), full job listing management, application tracking, and a recruiter-scoped analytics dashboard — all secured end-to-end with JWT tokens.

---

## Features

### Authentication & Security
- **JWT-based authentication** — stateless token generation and validation using `jjwt 0.12.5`
- **Role-based access control** — separate `USER` (job seeker) and `ADMIN` (recruiter) roles with Spring Security
- **BCrypt password hashing** — secure credential storage
- **Custom JWT filter** — `OncePerRequestFilter` validates every protected request
- **CORS support** — configured for Flutter web dev on any localhost port

### Job Management
- **Create job postings** (Recruiter only) — title, description, location, tech stack, experience level, company name
- **Dynamic job search** — filter by location, tech stack, company name, experience, or poster email
- **Pagination support** — efficient `Page<Job>` responses using Spring Data
- **Delete job postings** (owner only) — recruiter can only delete their own jobs
- **Recruiter-scoped listings** — admins see only jobs they created

### Application System
- **Apply to jobs** (Job Seeker) — one application per user per job (enforced via DB unique constraint)
- **Application status tracking** — `APPLIED → PENDING → ACCEPTED / REJECTED`
- **My applications view** — job seekers see their full application history with status
- **Recruiter applicant view** — recruiters see all applicants for each of their jobs
- **Status update** (Recruiter) — accept or reject individual applications

### User Profile
- **Create / update profile** — skills, location, phone number, years of experience
- **Profile retrieval** — fetch own profile details

### Recruiter Dashboard (Analytics)
- **Platform stats** — total registered users on the platform
- **Recruiter-scoped metrics** — jobs posted, total applications received, accepted and rejected counts
- All stats are scoped to the authenticated recruiter — no cross-recruiter data leakage

### API Documentation
- **Swagger UI** available at `/swagger-ui.html`
- **OpenAPI 3.0** spec via Springdoc
- Full try-it-out support for all endpoints

### Error Handling
- **Global exception handler** — `@RestControllerAdvice` catches all exceptions
- **Custom exceptions** — `ResourceNotFoundException` (404), `DuplicateResourceException` (409)
- **Structured error responses** — consistent `ErrorResponse` DTO across all error cases

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 4.0.3 |
| Security | Spring Security + JWT (jjwt 0.12.5) |
| Database | MySQL 8 (dev) / PostgreSQL (prod) |
| ORM | Spring Data JPA + Hibernate |
| Build Tool | Apache Maven |
| Documentation | Springdoc OpenAPI 2.5.0 / Swagger UI |
| Validation | Spring Validation (Bean Validation) |
| Code Generation | Lombok |
| Password Encoding | BCrypt |

---

## Architecture

```
src/main/java/com/JobPortal/JobPortal/
├── auth/                    # JWT generation, filter, login & registration
├── user/                    # UserInfo entity, repository, security adapter
├── Job/                     # Job entity, service, controller, DTOs
├── Application/             # Application entity, status enum, service, controller
├── Profile/                 # UserProfile entity, service, controller
├── admin/                   # Recruiter stats controller & response DTO
├── config/                  # Security config, CORS config, OpenAPI config
└── exception/               # Global handler, custom exceptions, error DTO
```

### Database Schema

```
users           jobs                applications          user_profiles
──────────      ──────────────      ──────────────────    ──────────────────
id (PK)         id (PK)             id (PK)               id (PK)
name            title               user_id (FK→users)    user_id (FK→users)
email (unique)  description         job_id (FK→jobs)      skills
password        location            status (ENUM)         location
roles           techStack           appliedAt             phone
                experienceRequired  UNIQUE(user,job)      experience
                companyName
                createdAt
                admin_id (FK→users)
```

---

## API Endpoints

### Public
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/register` | Register as job seeker or recruiter |
| `POST` | `/auth/login` | Login and receive JWT token |
| `GET` | `/auth/welcome` | Health check |

### Job Seeker (`USER` role)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/jobs` | Browse & search jobs (location, stack, company, experience) |
| `GET` | `/api/jobs/{id}` | Get full job details |
| `POST` | `/api/applications/apply/{jobId}` | Apply to a job |
| `GET` | `/api/applications/my` | View my application history |
| `POST` | `/api/profile/save` | Create or update my profile |
| `GET` | `/api/profile/me` | Get my profile |

### Recruiter (`ADMIN` role)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/jobs/admin` | Create a new job posting |
| `DELETE` | `/api/jobs/admin/{id}` | Delete a job posting |
| `GET` | `/api/applications/admin/job/{jobId}` | View applicants for a job |
| `PUT` | `/api/applications/admin/{appId}` | Update applicant status |
| `GET` | `/api/admin/stats` | View recruiter dashboard statistics |

---

## Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- MySQL 8+ (or PostgreSQL for production)

### 1. Clone the repository
```bash
git clone https://github.com/Rajg18/JobPortal-Backend.git
cd JobPortal-Backend
```

### 2. Configure the database
Create a MySQL database:
```sql
CREATE DATABASE portalDB;
```

### 3. Set environment variables (or edit `application.properties`)
```properties
DB_URL=jdbc:mysql://localhost:3306/portalDB
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET=your-256-bit-base64-encoded-secret
```

### 4. Run the application
```bash
./mvnw spring-boot:run
```

The API will start on `http://localhost:8080`.

### 5. Explore the API
Open Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Configuration

Key settings in `application.properties`:

```properties
# Database
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/portalDB}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:your_password}
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=${JWT_SECRET:your-base64-secret}
jwt.expiration.ms=3600000

# CORS (supports Flutter web on any localhost port)
cors.allowed-origins=${CORS_ALLOWED_ORIGINS:http://localhost:*}
```

---

## Production Deployment

The app supports PostgreSQL for cloud deployment (Railway, Render, etc.):

```properties
# Switch to PostgreSQL
spring.datasource.url=${DB_URL:jdbc:postgresql://host:5432/portalDB}
```

Set environment variables on your platform:
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- `JWT_SECRET` (use a strong 256-bit key)
- `CORS_ALLOWED_ORIGINS` (your frontend URL)

---

## Related Repository

**Frontend (Flutter):** [JobPortal-Frontend](https://github.com/Rajg18/JobPortal-Frontend)

---

## License

This project is open-source and available under the [MIT License](LICENSE).
