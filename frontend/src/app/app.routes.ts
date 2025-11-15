import { Routes } from '@angular/router';
import { authGuard } from './core/auth/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: '',
    redirectTo: 'setup/academic-sessions',
    pathMatch: 'full'
  },
  {
    path: 'setup/academic-sessions',
    loadComponent: () => import('./modules/setup/academic-sessions/components/academic-session-list.component').then(m => m.AcademicSessionListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'unauthorized',
    loadComponent: () => import('./features/auth/unauthorized/unauthorized.component').then(m => m.UnauthorizedComponent)
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];
