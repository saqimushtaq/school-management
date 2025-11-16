import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AcademicSessionStore } from '../store/academic-session.store';
import { AcademicSessionResponse } from '../models/academic-session.model';
import { AcademicSessionFormComponent } from './academic-session-form.component';

/**
 * Component to display list of Academic Sessions
 * Requirement: SET-01
 */
@Component({
  selector: 'app-academic-session-list',
  imports: [
    CommonModule
  ],
  template: `
    <div class="session-list-container">
      <div class="card">
        <div class="card-header">
          <div class="d-flex justify-content-between align-items-center">
            <h4 class="mb-0">Academic Sessions</h4>
            <div class="header-actions">
              <button type="button" class="btn btn-primary" (click)="openCreateDialog()">
                <i class="bi bi-plus-circle me-2"></i>
                Create Session
              </button>
            </div>
          </div>
        </div>

        <div class="card-body">
          @if (store.isLoading()) {
            <div class="loading-container">
              <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Loading...</span>
              </div>
            </div>
          } @else if (store.error()) {
            <div class="error-container">
              <p class="error-message text-danger">{{ store.error() }}</p>
              <button type="button" class="btn btn-primary" (click)="loadSessions()">Retry</button>
            </div>
          } @else {
            @if (store.sessions().length > 0) {
              <div class="table-responsive">
                <table class="table table-hover sessions-table">
                  <thead class="table-light">
                    <tr>
                      <th>Session Name</th>
                      <th>Start Date</th>
                      <th>End Date</th>
                      <th>Status</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    @for (session of store.sessions(); track session.id) {
                      <tr>
                        <td>{{ session.name }}</td>
                        <td>{{ session.startDate | date: 'mediumDate' }}</td>
                        <td>{{ session.endDate | date: 'mediumDate' }}</td>
                        <td>
                          @if (session.isCurrent) {
                            <span class="badge bg-success">Current</span>
                          } @else {
                            <button
                              type="button"
                              class="btn btn-sm btn-outline-primary"
                              (click)="setAsCurrent(session.id)"
                              [disabled]="store.isLoading()">
                              Set as Current
                            </button>
                          }
                        </td>
                        <td>
                          <div class="btn-group" role="group">
                            <button
                              type="button"
                              class="btn btn-sm btn-outline-primary"
                              (click)="openEditDialog(session)"
                              [disabled]="store.isLoading()"
                              title="Edit">
                              <i class="bi bi-pencil"></i>
                            </button>
                            <button
                              type="button"
                              class="btn btn-sm btn-outline-danger"
                              (click)="deleteSession(session)"
                              [disabled]="store.isLoading() || session.isCurrent"
                              title="Delete">
                              <i class="bi bi-trash"></i>
                            </button>
                          </div>
                        </td>
                      </tr>
                    }
                  </tbody>
                </table>
              </div>
            } @else {
              <div class="empty-state">
                <i class="bi bi-book empty-icon"></i>
                <p>No academic sessions found</p>
                <button type="button" class="btn btn-primary" (click)="openCreateDialog()">
                  Create First Session
                </button>
              </div>
            }
          }
        </div>
      </div>

      <!-- Toast Container -->
      @if (showToast) {
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
          <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header">
              <strong class="me-auto">{{ toastTitle }}</strong>
              <button type="button" class="btn-close" (click)="hideToast()"></button>
            </div>
            <div class="toast-body">
              {{ toastMessage }}
            </div>
          </div>
        </div>
      }
    </div>
  `,
  styles: [`
    .session-list-container {
      padding: 24px;
      max-width: 1200px;
      margin: 0 auto;
    }

    .card-header {
      background-color: #f8f9fa;
      border-bottom: 1px solid #dee2e6;
      padding: 1rem 1.5rem;
    }

    .header-actions {
      display: flex;
      gap: 8px;
    }

    .sessions-table {
      margin-top: 0;
    }

    .loading-container {
      display: flex;
      justify-content: center;
      padding: 48px;
    }

    .error-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 48px;
      gap: 16px;
    }

    .error-message {
      margin: 0;
    }

    .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 48px;
      gap: 16px;

      .empty-icon {
        font-size: 64px;
        color: rgba(0, 0, 0, 0.26);
      }

      p {
        color: rgba(0, 0, 0, 0.54);
        margin: 0;
      }
    }

    .btn-group {
      gap: 4px;
    }

    .toast-container {
      z-index: 1050;
    }

    .toast {
      min-width: 250px;
    }
  `]
})
export class AcademicSessionListComponent implements OnInit {
  protected readonly store = inject(AcademicSessionStore);
  private readonly modalService = inject(NgbModal);

  showToast = false;
  toastTitle = '';
  toastMessage = '';

  ngOnInit(): void {
    this.loadSessions();
  }

  loadSessions(): void {
    this.store.loadSessions();
  }

  openCreateDialog(): void {
    const modalRef = this.modalService.open(AcademicSessionFormComponent, {
      size: 'lg',
      centered: true
    });

    modalRef.componentInstance.mode = 'create';

    modalRef.result.then(
      (result) => {
        if (result) {
          this.displayToast('Success', 'Session created successfully');
        }
      },
      () => {
        // Modal dismissed
      }
    );
  }

  openEditDialog(session: AcademicSessionResponse): void {
    const modalRef = this.modalService.open(AcademicSessionFormComponent, {
      size: 'lg',
      centered: true
    });

    modalRef.componentInstance.mode = 'edit';
    modalRef.componentInstance.session = session;

    modalRef.result.then(
      (result) => {
        if (result) {
          this.displayToast('Success', 'Session updated successfully');
        }
      },
      () => {
        // Modal dismissed
      }
    );
  }

  setAsCurrent(id: number): void {
    this.store.setCurrentSession(id);
    this.displayToast('Success', 'Current session updated');
  }

  deleteSession(session: AcademicSessionResponse): void {
    if (confirm(`Are you sure you want to delete "${session.name}"?`)) {
      this.store.deleteSession(session.id);
      this.displayToast('Success', 'Session deleted successfully');
    }
  }

  private displayToast(title: string, message: string): void {
    this.toastTitle = title;
    this.toastMessage = message;
    this.showToast = true;
    setTimeout(() => this.hideToast(), 3000);
  }

  hideToast(): void {
    this.showToast = false;
  }
}
