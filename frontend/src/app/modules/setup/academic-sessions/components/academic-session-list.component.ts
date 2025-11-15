import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
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
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule
  ],
  template: `
    <div class="session-list-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Academic Sessions</mat-card-title>
          <div class="header-actions">
            <button mat-raised-button color="primary" (click)="openCreateDialog()">
              <mat-icon>add</mat-icon>
              Create Session
            </button>
          </div>
        </mat-card-header>

        <mat-card-content>
          @if (store.isLoading()) {
            <div class="loading-container">
              <mat-spinner></mat-spinner>
            </div>
          } @else if (store.error()) {
            <div class="error-container">
              <p class="error-message">{{ store.error() }}</p>
              <button mat-button color="primary" (click)="loadSessions()">Retry</button>
            </div>
          } @else {
            <table mat-table [dataSource]="store.sessions()" class="sessions-table">
              <!-- Name Column -->
              <ng-container matColumnDef="name">
                <th mat-header-cell *matHeaderCellDef>Session Name</th>
                <td mat-cell *matCellDef="let session">{{ session.name }}</td>
              </ng-container>

              <!-- Start Date Column -->
              <ng-container matColumnDef="startDate">
                <th mat-header-cell *matHeaderCellDef>Start Date</th>
                <td mat-cell *matCellDef="let session">{{ session.startDate | date: 'mediumDate' }}</td>
              </ng-container>

              <!-- End Date Column -->
              <ng-container matColumnDef="endDate">
                <th mat-header-cell *matHeaderCellDef>End Date</th>
                <td mat-cell *matCellDef="let session">{{ session.endDate | date: 'mediumDate' }}</td>
              </ng-container>

              <!-- Current Status Column -->
              <ng-container matColumnDef="isCurrent">
                <th mat-header-cell *matHeaderCellDef>Status</th>
                <td mat-cell *matCellDef="let session">
                  @if (session.isCurrent) {
                    <mat-chip class="current-chip">Current</mat-chip>
                  } @else {
                    <button
                      mat-stroked-button
                      color="accent"
                      (click)="setAsCurrent(session.id)"
                      [disabled]="store.isLoading()">
                      Set as Current
                    </button>
                  }
                </td>
              </ng-container>

              <!-- Actions Column -->
              <ng-container matColumnDef="actions">
                <th mat-header-cell *matHeaderCellDef>Actions</th>
                <td mat-cell *matCellDef="let session">
                  <button
                    mat-icon-button
                    color="primary"
                    (click)="openEditDialog(session)"
                    [disabled]="store.isLoading()">
                    <mat-icon>edit</mat-icon>
                  </button>
                  <button
                    mat-icon-button
                    color="warn"
                    (click)="deleteSession(session)"
                    [disabled]="store.isLoading() || session.isCurrent">
                    <mat-icon>delete</mat-icon>
                  </button>
                </td>
              </ng-container>

              <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
              <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
            </table>

            @if (store.sessions().length === 0) {
              <div class="empty-state">
                <mat-icon>school</mat-icon>
                <p>No academic sessions found</p>
                <button mat-raised-button color="primary" (click)="openCreateDialog()">
                  Create First Session
                </button>
              </div>
            }
          }
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .session-list-container {
      padding: 24px;
      max-width: 1200px;
      margin: 0 auto;
    }

    mat-card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 24px;
    }

    .header-actions {
      display: flex;
      gap: 8px;
    }

    .sessions-table {
      width: 100%;
      margin-top: 16px;
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
      color: #f44336;
      margin: 0;
    }

    .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 48px;
      gap: 16px;

      mat-icon {
        font-size: 64px;
        width: 64px;
        height: 64px;
        color: rgba(0, 0, 0, 0.26);
      }

      p {
        color: rgba(0, 0, 0, 0.54);
        margin: 0;
      }
    }

    .current-chip {
      background-color: #4caf50;
      color: white;
    }
  `]
})
export class AcademicSessionListComponent implements OnInit {
  protected readonly store = inject(AcademicSessionStore);
  private readonly dialog = inject(MatDialog);
  private readonly snackBar = inject(MatSnackBar);

  displayedColumns: string[] = ['name', 'startDate', 'endDate', 'isCurrent', 'actions'];

  ngOnInit(): void {
    this.loadSessions();
  }

  loadSessions(): void {
    this.store.loadSessions();
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(AcademicSessionFormComponent, {
      width: '600px',
      data: { mode: 'create' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.snackBar.open('Session created successfully', 'Close', { duration: 3000 });
      }
    });
  }

  openEditDialog(session: AcademicSessionResponse): void {
    const dialogRef = this.dialog.open(AcademicSessionFormComponent, {
      width: '600px',
      data: { mode: 'edit', session }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.snackBar.open('Session updated successfully', 'Close', { duration: 3000 });
      }
    });
  }

  setAsCurrent(id: number): void {
    this.store.setCurrentSession(id);
    this.snackBar.open('Current session updated', 'Close', { duration: 3000 });
  }

  deleteSession(session: AcademicSessionResponse): void {
    if (confirm(`Are you sure you want to delete "${session.name}"?`)) {
      this.store.deleteSession(session.id);
      this.snackBar.open('Session deleted successfully', 'Close', { duration: 3000 });
    }
  }
}
