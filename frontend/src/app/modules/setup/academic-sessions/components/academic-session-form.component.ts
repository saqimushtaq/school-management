import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AcademicSessionStore } from '../store/academic-session.store';
import { AcademicSessionResponse } from '../models/academic-session.model';

/**
 * Form component for creating/editing Academic Sessions
 * Requirement: SET-01
 */
@Component({
  selector: 'app-academic-session-form',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule
  ],
  template: `
    <h2 mat-dialog-title>{{ isEditMode ? 'Edit' : 'Create' }} Academic Session</h2>

    <mat-dialog-content>
      <form [formGroup]="sessionForm" class="session-form">
        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Session Name</mat-label>
          <input matInput formControlName="name" placeholder="e.g., 2024-2025">
          @if (sessionForm.get('name')?.hasError('required') && sessionForm.get('name')?.touched) {
            <mat-error>Session name is required</mat-error>
          }
        </mat-form-field>

        <mat-form-field appearance="outline" class="full-width">
          <mat-label>Start Date</mat-label>
          <input matInput [matDatepicker]="startPicker" formControlName="startDate">
          <mat-datepicker-toggle matIconSuffix [for]="startPicker"></mat-datepicker-toggle>
          <mat-datepicker #startPicker></mat-datepicker>
          @if (sessionForm.get('startDate')?.hasError('required') && sessionForm.get('startDate')?.touched) {
            <mat-error>Start date is required</mat-error>
          }
        </mat-form-field>

        <mat-form-field appearance="outline" class="full-width">
          <mat-label>End Date</mat-label>
          <input matInput [matDatepicker]="endPicker" formControlName="endDate">
          <mat-datepicker-toggle matIconSuffix [for]="endPicker"></mat-datepicker-toggle>
          <mat-datepicker #endPicker></mat-datepicker>
          @if (sessionForm.get('endDate')?.hasError('required') && sessionForm.get('endDate')?.touched) {
            <mat-error>End date is required</mat-error>
          }
          @if (sessionForm.hasError('dateRange') && sessionForm.get('endDate')?.touched) {
            <mat-error>End date must be after start date</mat-error>
          }
        </mat-form-field>

        <mat-checkbox formControlName="isCurrent" class="full-width">
          Set as current session
        </mat-checkbox>
      </form>
    </mat-dialog-content>

    <mat-dialog-actions align="end">
      <button mat-button (click)="onCancel()">Cancel</button>
      <button
        mat-raised-button
        color="primary"
        (click)="onSubmit()"
        [disabled]="sessionForm.invalid || store.isLoading()">
        {{ isEditMode ? 'Update' : 'Create' }}
      </button>
    </mat-dialog-actions>
  `,
  styles: [`
    .session-form {
      display: flex;
      flex-direction: column;
      gap: 16px;
      padding: 16px 0;
      min-width: 400px;
    }

    .full-width {
      width: 100%;
    }

    mat-dialog-content {
      overflow: visible;
    }

    mat-dialog-actions {
      padding: 16px 0;
    }
  `]
})
export class AcademicSessionFormComponent implements OnInit {
  protected readonly store = inject(AcademicSessionStore);
  private readonly fb = inject(FormBuilder);
  private readonly dialogRef = inject(MatDialogRef<AcademicSessionFormComponent>);
  private readonly data = inject<{ mode: 'create' | 'edit'; session?: AcademicSessionResponse }>(MAT_DIALOG_DATA);

  sessionForm!: FormGroup;
  isEditMode = false;

  ngOnInit(): void {
    this.isEditMode = this.data.mode === 'edit';
    this.initializeForm();
  }

  private initializeForm(): void {
    const session = this.data.session;

    this.sessionForm = this.fb.group({
      name: [session?.name || '', [Validators.required]],
      startDate: [session?.startDate ? new Date(session.startDate) : null, [Validators.required]],
      endDate: [session?.endDate ? new Date(session.endDate) : null, [Validators.required]],
      isCurrent: [session?.isCurrent || false]
    }, {
      validators: this.dateRangeValidator
    });
  }

  /**
   * Custom validator to ensure end date is after start date
   */
  private dateRangeValidator(form: FormGroup): { [key: string]: boolean } | null {
    const startDate = form.get('startDate')?.value;
    const endDate = form.get('endDate')?.value;

    if (startDate && endDate && startDate >= endDate) {
      return { dateRange: true };
    }

    return null;
  }

  onSubmit(): void {
    if (this.sessionForm.valid) {
      const formValue = this.sessionForm.value;

      // Convert dates to ISO string format
      const sessionData = {
        name: formValue.name,
        startDate: this.formatDate(formValue.startDate),
        endDate: this.formatDate(formValue.endDate),
        isCurrent: formValue.isCurrent
      };

      if (this.isEditMode && this.data.session) {
        this.store.updateSession({ id: this.data.session.id, data: sessionData });
      } else {
        this.store.createSession(sessionData);
      }

      this.dialogRef.close(true);
    }
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  /**
   * Format date to ISO string (YYYY-MM-DD)
   */
  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
