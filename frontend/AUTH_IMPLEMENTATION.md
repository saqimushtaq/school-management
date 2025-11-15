# Frontend Authentication Implementation

This document describes the authentication implementation for the School Management System frontend.

## Overview

The authentication system is built using Angular 20 with JWT (JSON Web Tokens) for secure authentication. It includes login functionality, HTTP interceptors, route guards, and token management.

## Architecture

### 1. Core Components

#### Authentication Service (`/core/auth/services/auth.service.ts`)
- **Purpose**: Manages authentication state and operations
- **Features**:
  - User login with username/password
  - Token storage in localStorage
  - User state management with Angular signals
  - Token expiration checking
  - Role-based authorization helpers
  - Automatic logout functionality

#### Auth Models (`/core/auth/models/auth.model.ts`)
- **LoginRequest**: Interface for login credentials
- **AuthResponse**: Interface for backend authentication response
- **UserRole**: Enum defining user roles (SUPER_ADMIN, ADMIN, TEACHER, ACCOUNTANT, PARENT)
- **AuthUser**: Interface for stored user data

### 2. Security Features

#### HTTP Interceptor (`/core/auth/interceptors/auth.interceptor.ts`)
- **Purpose**: Automatically adds JWT token to all HTTP requests
- **Behavior**:
  - Skips authentication endpoints (/auth/login, /auth/register)
  - Adds `Authorization: Bearer <token>` header to authenticated requests
  - Works seamlessly with all HTTP calls

#### Route Guards (`/core/auth/guards/auth.guard.ts`)
- **authGuard**: Protects routes requiring authentication
  - Redirects to login if not authenticated
  - Checks token expiration
  - Preserves return URL for post-login redirection

- **roleGuard**: Factory function for role-based access control
  - Usage: `canActivate: [roleGuard(['ADMIN', 'SUPER_ADMIN'])]`
  - Redirects to unauthorized page if user lacks required role

### 3. User Interface

#### Login Component (`/features/auth/login/`)
- **Features**:
  - Material Design UI with form validation
  - Password visibility toggle
  - Loading state during authentication
  - Error message display
  - Responsive design

#### Unauthorized Component (`/features/auth/unauthorized/`)
- **Purpose**: Display access denied message
- **Features**:
  - User-friendly error page
  - Navigation options (back, login)

#### App Header (`app.ts`, `app.html`, `app.scss`)
- **Features**:
  - Displays current user information
  - User menu with logout option
  - Conditional rendering (only shown when authenticated)
  - Responsive design

## Configuration

### Routes (`app.routes.ts`)
```typescript
- /login - Public login page
- / - Redirects to setup/academic-sessions
- /setup/academic-sessions - Protected with authGuard
- /unauthorized - Access denied page
- /** - Wildcard redirects to login
```

### App Config (`app.config.ts`)
- Registers `authInterceptor` for HTTP requests
- Configures Angular providers

## Usage

### Protecting Routes
```typescript
{
  path: 'admin-panel',
  component: AdminComponent,
  canActivate: [authGuard]
}
```

### Role-Based Protection
```typescript
{
  path: 'super-admin',
  component: SuperAdminComponent,
  canActivate: [roleGuard(['SUPER_ADMIN'])]
}
```

### Accessing Auth State in Components
```typescript
import { AuthService } from './core/auth/services/auth.service';

export class MyComponent {
  private authService = inject(AuthService);

  // Reactive signals
  isAuthenticated = this.authService.isAuthenticated;
  currentUser = this.authService.currentUser;

  // Check roles
  isAdmin = this.authService.hasRole('ADMIN');
  hasAccess = this.authService.hasAnyRole(['ADMIN', 'SUPER_ADMIN']);
}
```

## API Integration

The frontend integrates with the following backend endpoints:

- **POST /api/auth/login**
  - Request: `{ username: string, password: string }`
  - Response: `{ token: string, username: string, role: UserRole, userId: number }`

## Security Considerations

1. **Token Storage**: JWT tokens are stored in localStorage
2. **Token Expiration**: Automatic checking of token expiration
3. **Automatic Logout**: Users are logged out when token expires
4. **Secure Headers**: Bearer token added to all authenticated requests
5. **Route Protection**: Unauthorized users cannot access protected routes
6. **Role-Based Access**: Fine-grained access control based on user roles

## Future Enhancements

- [ ] Implement refresh token mechanism
- [ ] Add remember me functionality
- [ ] Implement token renewal before expiration
- [ ] Add session timeout warnings
- [ ] Implement multi-factor authentication
- [ ] Add password reset functionality
- [ ] Implement account lockout after failed attempts

## Development

### Running the Application
```bash
cd frontend
npm install
npm start
```

### Building for Production
```bash
npm run build
```

### Testing
- Navigate to `http://localhost:4200/login`
- Use valid credentials from the backend
- Test protected routes without authentication
- Test role-based access control

## Troubleshooting

### Issue: 401 Unauthorized Errors
- Check if token is present in localStorage
- Verify token hasn't expired
- Ensure backend is running and accessible

### Issue: Routes Not Protected
- Verify `authGuard` is added to route configuration
- Check that interceptor is registered in `app.config.ts`

### Issue: User Not Redirected After Login
- Check console for routing errors
- Verify return URL is being preserved
- Ensure protected routes are configured correctly
