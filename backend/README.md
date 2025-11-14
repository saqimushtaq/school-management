# SmartSchool Backend

Spring Boot backend for the SmartSchool Management System.

## Technology Stack

- **Framework:** Spring Boot 3.5.1
- **Language:** Java 21
- **Database:** PostgreSQL 16+
- **Database Migration:** Flyway
- **Authentication:** JWT (JJWT)
- **DTO Mapping:** MapStruct
- **Boilerplate Reduction:** Lombok
- **API Documentation:** SpringDoc OpenAPI 2
- **Logging:** SLF4J

## Project Structure

```
src/main/java/com/smartschool/
├── config/          # Configuration classes (Security, OpenAPI, etc.)
├── controller/      # REST Controllers
├── dto/            # Data Transfer Objects (Records)
├── entity/         # JPA Entities
├── exception/      # Custom Exceptions and Global Exception Handler
├── mapper/         # MapStruct Mappers
├── repository/     # JPA Repositories
├── service/        # Business Logic Services
├── security/       # JWT and Spring Security Components
└── util/           # Utility Classes

src/main/resources/
├── db/migration/   # Flyway Migration Scripts
├── static/         # Static Resources
├── templates/      # Thymeleaf Templates (for PDF generation)
└── application.yml # Application Configuration
```

## Prerequisites

- Java 21 or higher
- Maven 3.8+
- PostgreSQL 16+

## Setup

1. Create PostgreSQL database:
   ```sql
   CREATE DATABASE smartschool_db;
   ```

2. Update database credentials in `application.yml` or set environment variables:
   ```bash
   export DB_USERNAME=your_username
   export DB_PASSWORD=your_password
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Documentation

Once the application is running, access:
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- API Docs: http://localhost:8080/api/docs

## Database Migrations

Flyway migrations are located in `src/main/resources/db/migration/`.
Follow the naming convention: `V{version}__{description}.sql`

Example: `V1__create_users_table.sql`

## Development

The project uses Spring Boot DevTools for automatic restart during development.
