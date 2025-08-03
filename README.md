# CineApp

> A web-based movie management system built with Spring Boot and Thymeleaf.

This README is also available in [Spanish ES](./README.es.md)

**CineApp** is a backend + web application for managing movie listings,
schedules, banners, news, and users in a cinema context.  
It offers role-based access, form validation, and admin functionalities
for managing content efficiently through a web UI.

---

## Technologies Used

| Tool              | Purpose                            |
|-------------------|------------------------------------|
| Java 17           | Main programming language          |
| Spring Boot 3.3.2 | Backend framework                  |
| Spring Web        | REST/web endpoints                 |
| Thymeleaf         | Server-side HTML templating engine |
| Spring Data JPA   | ORM and persistence layer          |
| Spring Security   | Authentication & role management   |
| Spring Validation | Backend input validations          |
| MySQL 8           | Relational database                |
| H2                | In-memory database for testing     |
| DevTools          | Hot reload during development      |
| Docker Compose    | Container for MySQL                |
| JUnit 5           | Unit & integration testing         |

---

##  Features

- **Authentication & Authorization** with Spring Security
-  **Banners Management** (add, list, delete)
-  **Movie Management** (CRUD, genre filter, status)
-  **Schedules** per movie and date
-  **News Section** (create, list, recent)
-  **User and Profile Management**
-  **Show movies by date**
-  **Paginated listing** of movies and schedules

---

##  Validations & Rules
### Examples

-  **Role-based access** for protected routes
-  **Only active movies** are shown to public users
-  **Schedules** filtered by date and movie
-  **Custom genre list** provided for consistent tagging
-  **Latest news filter** shows only active and recent posts
-  **BCrypt password encryption** (if enabled)

---

## Project Structure

- controllers/     — Web controllers (handle views and routes)
- config/          — General configuration (application settings, etc.)
- models/          — Entities and JPA 
- repositories/    — repositories
- security/        — security config
- service/         — Service interfaces and business logic implementations
- resources/       — Configuration files (application.properties, etc.)
- templates/       — Thymeleaf HTML templates
- static/          — Static assets (CSS, JS, images)
- util/            — Utility classes (roles, validators, helpers)
- test/            — Unit and integration tests

### Architecture
The project is designed with a layered MVC architecture, allowing for a
clear separation of responsibilities between the presentation layer,
business logic, and data persistence. It also ensures modularity and
scalability by following common Spring Boot practices.

---
##  Local Installation

### 1. Prerequisites

- Java 17 installed
- Docker and Docker Compose
- Maven

### 2. Clone the repository
git clone https://github.com/alejandrorivera22/cineapp.git
cd cineapp

### 3. Custom Configuration (Image Path)
- On Windows:
  **ciena.ruta.images=C:\\Users\\YourUser\\cineapp\\images\\**
- On Linux/macOS:
  **ciena.ruta.images=/home/youruser/cineapp/images/**

### 4. Sample Images
- **Important:** You must copy the images located in resources/images into the folder you defined in ciena.ruta.images
  so the application can display them correctly at runtime.

### 5. Start MySQL
- docker-compose up -d

### 6. Build and run the application
- ./mvnw clean install
- ./mvnw spring-boot:run

### 7. Access to the web:
- http://localhost:8080

---

## Predefined Test Users

These users are preloaded into the database (`data.sql`)  
and can be used to simulate authentication and authorization  
according to the different roles available in the system.

| Rol      | Username       | Contraseña |
|----------|----------------|------------|
| EDITOR    | `luis`         | `luis123`  |
| GERENTE   | `marisol `     | `mari123`  |

> Passwords are encrypted using BCrypt.  
> These credentials are provided for local testing purposes only.

---
**Alejandro Rivera**
- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin)](www.linkedin.com/in/alejandro-rivera-verdayes-dev)
- [![GitHub](https://img.shields.io/badge/GitHub-000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/alejandrorivera22)
