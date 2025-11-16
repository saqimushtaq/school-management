# SmartSchool - School Management System

A comprehensive, bespoke School Management System built with modern technologies.

## Project Overview

SmartSchool is designed to streamline the operational needs of educational institutions, covering everything from student enrollment to fee management, attendance tracking, and academic reporting.

## Technology Stack

### Backend
- **Framework:** Spring Boot 3.5.1
- **Language:** Java 21
- **Database:** PostgreSQL 16+
- **Authentication:** JWT (JJWT)
- **API Documentation:** SpringDoc OpenAPI 2

### Frontend
- **Framework:** Angular 20+
- **Language:** TypeScript
- **UI Library:** Bootstrap 5 with ng-bootstrap
- **State Management:** NgRx Signal Stores

## Project Structure

```
school-management/
├── backend/              # Spring Boot backend application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/smartschool/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
├── frontend/             # Angular frontend application
│   ├── src/
│   │   ├── app/
│   │   ├── assets/
│   │   └── environments/
│   └── package.json
│
└── docs/                 # Project documentation
    ├── requirements.md
    ├── functional-requirements.md
    └── techinal-decision.md
```

## Core Modules

1. **System Setup & Configuration** - Academic sessions, classes, sections, subjects
2. **Staff Management & Payroll** - Staff profiles, teacher assignments, payroll
3. **Student Information System** - Enrollment, profiles, class promotion
4. **Attendance Management** - Daily attendance with automated SMS alerts
5. **Fee Management & Accounting** - Fee structures, discounts, voucher generation
6. **Academics & Examinations** - Exam setup, marks entry, report cards
7. **Communication Hub** - Bulk SMS messaging to parents
8. **User Access & Security** - Role-based access control, authentication

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.8+
- Node.js 18+ and npm
- PostgreSQL 16+

### Backend Setup

1. Navigate to backend directory:
   ```bash
   cd backend
   ```

2. Create PostgreSQL database:
   ```sql
   CREATE DATABASE smartschool_db;
   ```

3. Configure database credentials in `src/main/resources/application.yml`

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

Backend will be available at: http://localhost:8080

### Frontend Setup

1. Navigate to frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Run development server:
   ```bash
   npm start
   ```

Frontend will be available at: http://localhost:4200

## API Documentation

Once the backend is running, access Swagger UI at:
http://localhost:8080/swagger-ui.html

## Documentation

Detailed documentation is available in the `docs/` directory:
- [Requirements](docs/requirements.md)
- [Functional Requirements](docs/functional-requirements.md)
- [Technical Decisions](docs/techinal-decision.md)

## Development Workflow

1. Backend changes should include Flyway migrations for database schema updates
2. Frontend follows Angular style guide with standalone components
3. Use feature branches for development
4. All API endpoints should be documented with OpenAPI annotations

## License

Proprietary - All rights reserved
