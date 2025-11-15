import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'setup/academic-sessions',
    pathMatch: 'full'
  },
  {
    path: 'setup/academic-sessions',
    loadComponent: () => import('./modules/setup/academic-sessions/components/academic-session-list.component').then(m => m.AcademicSessionListComponent)
  }
];
