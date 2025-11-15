import { inject } from '@angular/core';
import { Router, type CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Auth Guard to protect routes that require authentication
 */
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Check if user is authenticated and token is not expired
  if (authService.isAuthenticated() && !authService.isTokenExpired()) {
    return true;
  }

  // Store the attempted URL for redirecting after login
  const returnUrl = state.url;

  // Redirect to login page
  router.navigate(['/login'], {
    queryParams: { returnUrl }
  });

  return false;
};

/**
 * Role-based guard factory
 * Usage: canActivate: [roleGuard(['ADMIN', 'SUPER_ADMIN'])]
 */
export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
  return (route, state) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (!authService.isAuthenticated() || authService.isTokenExpired()) {
      router.navigate(['/login']);
      return false;
    }

    if (authService.hasAnyRole(allowedRoles)) {
      return true;
    }

    // User doesn't have required role, redirect to unauthorized page
    router.navigate(['/unauthorized']);
    return false;
  };
};
