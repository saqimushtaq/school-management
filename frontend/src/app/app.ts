import { Component, inject, computed, signal } from '@angular/core';
import { Router, RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from './core/auth/services/auth.service';

@Component({
  selector: 'app-root',
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    NgbDropdownModule
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'TaleemTrack';

  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  // Sidebar state
  protected readonly isSidebarCollapsed = signal(false);

  // Computed signals for reactive state
  protected readonly isAuthenticated = this.authService.isAuthenticated;
  protected readonly currentUser = this.authService.currentUser;

  // Check if current route is login page
  protected readonly isLoginPage = computed(() =>
    this.router.url === '/login'
  );

  /**
   * Toggle sidebar collapsed state
   */
  toggleSidebar(): void {
    this.isSidebarCollapsed.update(value => !value);
  }

  /**
   * Get page title based on current route
   */
  getPageTitle(): string {
    const url = this.router.url;

    if (url.includes('/dashboard')) return 'Dashboard';
    if (url.includes('/setup/academic-sessions')) return 'Academic Sessions';
    if (url.includes('/setup/classes')) return 'Classes & Sections';
    if (url.includes('/setup/subjects')) return 'Subjects';
    if (url.includes('/students')) return 'Students';
    if (url.includes('/staff')) return 'Staff Management';
    if (url.includes('/attendance')) return 'Attendance';
    if (url.includes('/examinations')) return 'Examinations';
    if (url.includes('/fees')) return 'Fee Management';
    if (url.includes('/payroll')) return 'Payroll';
    if (url.includes('/messages')) return 'SMS Messages';

    return 'TaleemTrack';
  }

  /**
   * Logout user
   */
  logout(): void {
    this.authService.logout();
  }
}
