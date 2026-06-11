# JobPortal — Spring Boot Backend

## Project Overview
A REST API backend for a Job Portal application. Built with Spring Boot 3, Spring Security (JWT), and MySQL via Spring Data JPA.

## Tech Stack
| Layer        | Technology                        |
|--------------|-----------------------------------|
| Language     | Java 17                           |
| Framework    | Spring Boot 3                     |
| Security     | Spring Security + JWT (jjwt)      |
| Database     | MySQL 8 via Spring Data JPA       |
| Docs         | Springdoc OpenAPI / Swagger UI    |
| Build        | Maven                             |

## Running Locally
```bash
# 1. Start MySQL and create the schema
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS portalDB;"

# 2. Run the Spring Boot app
./mvnw spring-boot:run
# OR open in IntelliJ and run JobPortalApplication.java

# 3. Swagger UI available at
http://localhost:8080/swagger-ui.html
```

## Environment Variables (override in production)
| Variable              | Default                       | Purpose                        |
|-----------------------|-------------------------------|--------------------------------|
| `DB_URL`              | jdbc:mysql://localhost:3306/portalDB | Database JDBC URL       |
| `DB_USERNAME`         | root                          | DB user                        |
| `DB_PASSWORD`         | (set in properties)           | DB password                    |
| `JWT_SECRET`          | (base64 key in properties)    | JWT signing secret             |
| `CORS_ALLOWED_ORIGINS`| http://localhost:*            | Allowed frontend origins       |

## Package Structure
```
com.JobPortal.JobPortal
├── auth/           Login, Register, JWT filter, JWT service
├── user/           UserInfo entity, repository, UserDetails adapter
├── Job/            Job entity, DTO, service, controller, repository
├── Application/    Application entity, DTO, service, controller, repository
├── Profile/        UserProfile entity, DTO, service, controller
├── admin/          AdminController, StatsResponseDTO
├── config/         SecurityConfig, CorsConfig, OpenApiConfig
└── exception/      GlobalExceptionHandler, custom exceptions
```

## Key Design Decisions
- **Roles without ROLE_ prefix** — stored as `USER` or `ADMIN`, enforced with `hasAuthority()` not `hasRole()`
- **JWT contains roles claim** — `generateToken(email, roles)` embeds the role so the Flutter client can decode it without an extra API call
- **CORS uses `setAllowedOriginPatterns`** — supports wildcard `http://localhost:*` for Flutter web dev
- **Recruiter-scoped stats** — `GET /api/admin/stats` uses `Authentication` to return only the calling recruiter's data, never platform-wide aggregates
- **Ownership filter on job search** — `GET /api/jobs?postedBy=email` filters by creator; admin portal passes the logged-in email automatically

## API Endpoints Summary
| Method | Path                                | Role  | Description                        |
|--------|-------------------------------------|-------|------------------------------------|
| POST   | /auth/register                      | Public| Register new user                  |
| POST   | /auth/login                         | Public| Login → returns JWT                |
| GET    | /api/jobs                           | Any   | Search/list jobs (filters optional)|
| POST   | /api/jobs/admin                     | ADMIN | Create job posting                 |
| DELETE | /api/jobs/admin/{id}                | ADMIN | Delete job                         |
| POST   | /api/applications/apply/{jobId}     | USER  | Apply to a job                     |
| GET    | /api/applications/my                | USER  | My applications                    |
| GET    | /api/applications/admin/job/{jobId} | ADMIN | Applicants for a specific job      |
| PUT    | /api/applications/admin/{appId}     | ADMIN | Update application status          |
| GET    | /api/profile/me                     | USER  | Get my profile                     |
| POST   | /api/profile/save                   | USER  | Create / update my profile         |
| GET    | /api/admin/stats                    | ADMIN | This recruiter's own stats         |
