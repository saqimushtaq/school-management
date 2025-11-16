### **SmartSchool - Project Technical & Architectural Decisions**

**Document Version:** 1.0
**Date:** November 25, 2025

This document outlines the key architectural, technological, and procedural decisions made for the "SmartSchool" bespoke School Management System project. All development should adhere to the standards set forth herein.

---

### **1. Project Management & Process**

*   **Development Methodology:** Agile, with phased delivery and milestone reviews.
*   **Requirements Management:** A finalized Functional Requirements document serves as the project scope.
*   **Version Control:** **Git**. The repository will be hosted on GitHub.
---

### **2. Technology Stack**

#### **Backend**
*   **Framework:** **Spring Boot 3.5x**
*   **Language:** Java 21+
*   **Database:** **PostgreSQL 16+**
*   **Database Migration:** **Flyway**. All schema changes will be versioned as SQL scripts in `src/main/resources/db/migration`.
*   **Authentication:** **JWT (JSON Web Tokens)** using the **JJWT** library for token creation and parsing, integrated with Spring Security.
*   **Data Transfer:** **MapStruct** for mapping between JPA Entities and DTOs.
*   **Boilerplate Reduction:** **Lombok** for Entities, DTOs, and Services.
*   **Java Record** types for immutable DTOs.
*   **API Documentation:** **SpringDoc OpenAPI 2** to auto-generate Swagger UI documentation.
*   **Logging:** **SLF4J**
*   **Exception Handling:** Centralized using a `@ControllerAdvice` class.

#### **Frontend**
*   **Framework:** **Angular 20+**
*   **Language:** TypeScript
*   **UI Library:** **Bootstrap 5** with **ng-bootstrap** for Angular integration.
*   **State Management:** **NgRx Signal Stores**.
*   **Forms:** **ReactiveFormsModule** for all complex forms.
*   **Validation:**  with Reactive Forms.
*   **HTTP Client:** Angular's built-in `HttpClient`.

---

### **3. Architectural Decisions**

*   **Architecture:** **Monolithic**. The application will be a single, deployable unit.
*   **Data Storage Strategy:**
    *   Files (student photos, documents) will be stored in a local directory on the developer's machine.
    *   **Implementation:** This will be handled via a `FileStorageService` in the backend that manages file uploads and retrievals.
*   **Reporting Engine:** **HTML-to-PDF**. We will use a server-side templating engine (like Thymeleaf) to render data into an HTML template styled with CSS, and then convert it to a PDF using a library like Flying Saucer or a command-line tool like WKHTMLtoPDF.

---

### **4. UI/UX & Theming Strategy**

*   **Goal:** To create a professional, unique, and branded application with a modern, clean design.
*   **Method:** Customization of Bootstrap 5 via a centralized **SCSS theme** and custom CSS variables.
*   **Key Customization Areas:**
    1.  **Custom Theme:** Define a unique color palette (primary, accent, semantic colors) and typography using CSS custom properties in `styles.scss`.
    2.  **Component Styling:** Override Bootstrap component styles using custom SCSS to create a distinctive look for cards, buttons, tables, forms, and modals.
    3.  **Custom Layouts:** Build custom layout components, including a professional sidebar navigation with collapsible menu and top header with user menu.
    4.  **Responsive Design:** Ensure all components are fully responsive with mobile-first approach and proper breakpoints.

---

### **5. Key Features & Business Logic Decisions**

*   **Admissions:** Admin-only creation of student records. No public-facing application form.
*   **Data Organization:** **Session-wise**. All student academic data (enrollment, marks, attendance) will be explicitly linked to an Academic Session for historical integrity.
*   **Attendance:** Manual marking by teachers with **automated SMS alerts** to parents for absences.
*   **Fee Management:** Supports complex fee structures, **discount management**, and manual payment recording for Cash, Bank Transfer, and Online methods.
*   **Staff & Payroll:** A basic module for staff management and salary slip generation is included.
*   **User Roles:** Role-Based Access Control (RBAC) will be strictly enforced for all users (Admin, Teacher, Accountant, Parent).

---

### **6. Development Workflow & Productivity**

*   **Backend:** Use **Spring Boot DevTools** for automatic restarts and faster iteration.
*   **Frontend:** Use the **Angular CLI** for all code generation (components, services, etc.).
*   **UI Components:** Leverage **ng-bootstrap** components (modals, dropdowns, accordions) for interactive UI elements with native Bootstrap integration.
*   **State Management:** Utilize **NgRx Signal Stores** to manage shared state, loading indicators, and error handling centrally.
*   **API Design:** Adhere to a RESTful API design. The backend will serve JSON, and the frontend will consume it via the `HttpClient`.

---

This document is a living record of the project's foundation. Any future deviation from these decisions should be carefully considered and this document should be updated accordingly.
