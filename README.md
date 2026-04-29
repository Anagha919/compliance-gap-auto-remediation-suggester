# Compliance Gap Auto-Remediation Suggester

A production-style Spring Boot backend that manages compliance records with secure authentication, role-based access control, caching, email notifications, and scheduled reminders.

---

## Features

* JWT Authentication (Login / Register / Refresh)
* Role-Based Access Control (RBAC)
* CRUD APIs with Pagination
* Global Exception Handling (400 / 404 / 500)
* Caching with Spring Cache
* Email Notifications (JavaMailSender)
* Scheduled Reminders (@Scheduled)
* Unit Testing (JUnit 5 + Mockito)
* Docker-ready configuration

---

## Architecture

```text
Client (Postman / UI)
        |
        v
Controller Layer  -->  Service Layer  -->  Repository Layer  -->  PostgreSQL
        |
        v
Security Layer (JWT + RBAC)
        |
        v
Caching Layer (Spring Cache)
        |
        v
Email + Scheduler
```

---

## Tech Stack

* Java 17
* Spring Boot 3
* Spring Security + JWT
* Spring Data JPA (Hibernate)
* PostgreSQL
* Maven
* Redis (optional)
* JavaMailSender
* JUnit 5 + Mockito

---

## Prerequisites

Make sure you have installed:

* Java 17+
* Maven
* PostgreSQL
* Git

(Optional)

* Docker Desktop

---

## Setup Instructions

### Clone repository

```bash
git clone https://github.com/your-username/your-repo-name.git
cd backend
```

---

### Configure database

Create DB in PostgreSQL:

```sql
CREATE DATABASE compliance_db;
```

---

### Configure application.yml

Update:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/compliance_db
    username: postgres
    password: your_password
```

---

### Configure email (App Password)

```yaml
spring:
  mail:
    username: your_email@gmail.com
    password: your_app_password
```

---

### Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

---

## API Endpoints

### Auth

* `POST /auth/register`
* `POST /auth/login`
* `POST /auth/refresh`

### Records

* `GET /api/records/all`
* `GET /api/records/{id}`
* `POST /api/records/create`
* `PUT /api/records/{id}`
* `DELETE /api/records/{id}`

---

## Environment Variables (.env reference)

| Variable       | Description  | Example                                             |
| -------------- | ------------ | --------------------------------------------------- |
| DB_URL         | Database URL | jdbc:postgresql://localhost:5432/compliance_db      |
| DB_USER        | DB username  | postgres                                            |
| DB_PASS        | DB password  | password                                            |
| MAIL_USER      | Gmail ID     | [your_email@gmail.com](mailto:your_email@gmail.com) |
| MAIL_PASS      | App password | xxxxxxxxxxxxxxxx                                    |
| JWT_SECRET     | Secret key   | mysecretkey                                         |
| JWT_EXPIRATION | Token expiry | 86400000                                            |

---

## Running Tests

```bash
mvn test
```

---

## Docker (Optional)

```bash
docker compose up --build
```

---

## Email & Scheduler

* Sends reminder emails for OPEN tasks
* Deadline alerts for upcoming tasks
* Configurable cron schedule

---

## Data Seeding

* Automatically inserts 30 sample compliance records on startup

---

