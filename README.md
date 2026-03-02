# Employee Management System API

A RESTful API built with **Java and Spring Boot** for managing employees, departments, and leave requests. Designed with a clean layered architecture and production-ready patterns including JWT authentication, role-based access control, and automated email workflows.

---

## Tech Stack

- **Java / Spring Boot**
- **PostgreSQL**
- **Spring Security + JWT**
- **Spring Data JPA / Hibernate**
- **Java Mail Sender**
- **JUnit / Mockito**

---

## Features

- **Authentication** — Signup with email verification token, signin with JWT, forgot password, and password reset via email
- **Employee Management** — Full CRUD with paginated responses and next/previous URL generation
- **Department Management** — Full CRUD for departments linked to employees
- **Leave Requests** — Create and retrieve leave requests per employee
- **JWT Security** — Stateless authentication with token-based authorization
- **Role-Based Access Control** — Route-level protection based on user roles
- **Email Workflows** — Automated email verification on signup and secure password reset flow
- **Pagination** — Custom paginated responses with navigation URLs
- **Standardized Responses** — Global response wrapper (`GlobalResponse`) for consistent API output
- **Centralized Exception Handling** — Uniform error responses across all endpoints
- **Unit Testing** — Service and repository layers covered with JUnit and Mockito

---

## API Endpoints

### Auth (`/auth`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/signup` | Register with email verification token |
| POST | `/auth/signin` | Login and receive JWT |
| POST | `/auth/forgot-password/{username}` | Initiate password reset via email |
| POST | `/auth/reset-password` | Reset password using token |

### Employees (`/employees`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/employees` | Get all employees (paginated) |
| GET | `/employees/{id}` | Get a single employee |
| POST | `/employees` | Create a new employee |
| PUT | `/employees/{id}` | Update an employee |
| DELETE | `/employees/{id}` | Delete an employee |
| POST | `/employees/{id}/leave-request` | Submit a leave request |
| GET | `/employees/{id}/leave-requests` | Get all leave requests for an employee |

### Departments (`/departments`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/departments` | Get all departments |
| GET | `/departments/{id}` | Get a single department |
| POST | `/departments` | Create a department |
| PUT | `/departments/{id}` | Update a department |
| DELETE | `/departments/{id}` | Delete a department |

---

## Architecture

```
src/
├── controller/        # HTTP layer — handles requests and responses
├── service/           # Business logic layer
├── repository/        # Data access layer (Spring Data JPA)
├── model/             # JPA entities
├── dto/               # Request/Response DTOs with validation
├── security/          # JWT filter, config, and Spring Security setup
├── exception/         # Global exception handler
└── email/             # Mail sender service and templates
```

---

## Getting Started

```bash
# Clone the repo
git clone https://github.com/3ayary/employee-management-system
cd employee-management-system

# Configure application.properties
# Set your PostgreSQL URL, credentials, JWT secret, and mail settings

# Build and run
./mvnw spring-boot:run
```

---

## What I Learned Building This

This was my first complete backend system in Java. The goal was to understand how enterprise-grade APIs are structured — from JWT auth flows to email-based verification and clean layered separation. Spring Security was the hardest part; getting the filter chain right took real debugging, and that's where I learned the most.
