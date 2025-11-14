# SmartSchool Frontend

Angular frontend for the SmartSchool Management System.

## Technology Stack

- **Framework:** Angular 20+
- **Language:** TypeScript
- **UI Library:** Angular Material 3 (M3)
- **State Management:** NgRx Signal Stores
- **Forms:** ReactiveFormsModule
- **HTTP Client:** Angular HttpClient

## Project Structure

```
src/app/
├── core/                    # Core module (singletons, guards, interceptors)
│   ├── auth/               # Authentication services and models
│   ├── guards/             # Route guards
│   ├── interceptors/       # HTTP interceptors (JWT, error handling)
│   ├── services/           # Core services
│   └── models/             # Core data models
│
├── shared/                  # Shared module (reusable components)
│   ├── components/         # Shared components
│   ├── directives/         # Shared directives
│   ├── pipes/              # Shared pipes
│   └── validators/         # Custom form validators
│
├── features/                # Feature modules
│   ├── auth/               # Authentication (login, forgot password)
│   ├── dashboard/          # Main dashboard
│   ├── setup/              # System setup & configuration
│   ├── staff/              # Staff management & payroll
│   ├── students/           # Student information system
│   ├── attendance/         # Attendance management
│   ├── fees/               # Fee management & accounting
│   ├── exams/              # Academics & examinations
│   └── communication/      # Communication hub
│
├── layout/                  # Layout components
│   ├── header/             # Header component
│   ├── sidebar/            # Sidebar navigation
│   └── footer/             # Footer component
│
└── assets/                  # Static assets
    ├── images/             # Images
    ├── styles/             # Global styles and themes
    └── icons/              # Icons
```

## Prerequisites

- Node.js 18+ and npm
- Angular CLI 20+

## Setup

1. Install dependencies:
   ```bash
   npm install
   ```

2. Update environment configuration in `src/environments/`:
   - `environment.ts` for development
   - `environment.prod.ts` for production

3. Run development server:
   ```bash
   npm start
   ```

4. Open browser at `http://localhost:4200`

## Development

### Generate Components
```bash
ng generate component features/feature-name/component-name
```

### Generate Services
```bash
ng generate service core/services/service-name
```

### Build for Production
```bash
npm run build
```

The build artifacts will be stored in the `dist/` directory.

## Path Aliases

The project uses TypeScript path aliases for cleaner imports:
- `@core/*` → `src/app/core/*`
- `@shared/*` → `src/app/shared/*`
- `@features/*` → `src/app/features/*`
- `@layout/*` → `src/app/layout/*`
- `@environments/*` → `src/environments/*`

## Coding Standards

- Use standalone components (Angular 20+)
- Use Reactive Forms for all forms
- Use NgRx Signal Stores for state management
- Follow Angular style guide
- Use TypeScript strict mode

## Custom Theme

The project uses a customized Angular Material 3 theme to avoid the default "Google look".
Theme customization is in `src/assets/styles/_theme.scss`.
