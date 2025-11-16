import { Component, inject, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal, NgbDatepickerModule, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
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
    NgbDatepickerModule
  ],
  template: `
    <div class="modal-header">
      <h5 class="modal-title">{{ isEditMode ? 'Edit' : 'Create' }} Academic Session</h5>
      <button type="button" class="btn-close" (click)="onCancel()"></button>
    </div>

    <div class="modal-body">
      <form [formGroup]="sessionForm" class="session-form">
        <div class="mb-3">
          <label for="sessionName" class="form-label">Session Name</label>
          <input
            type="text"
            class="form-control"
            id="sessionName"
            formControlName="name"
            placeholder="e.g., 2024-2025"
            [class.is-invalid]="sessionForm.get('name')?.hasError('required') && sessionForm.get('name')?.touched">
          @if (sessionForm.get('name')?.hasError('required') && sessionForm.get('name')?.touched) {
            <div class="invalid-feedback">
              Session name is required
            </div>
          }
        </div>

        <div class="mb-3">
          <label for="startDate" class="form-label">Start Date</label>
          <div class="input-group">
            <input
              type="text"
              class="form-control"
              id="startDate"
              placeholder="yyyy-mm-dd"
              formControlName="startDate"
              ngbDatepicker
              #startDatePicker="ngbDatepicker"
              [class.is-invalid]="sessionForm.get('startDate')?.hasError('required') && sessionForm.get('startDate')?.touched"
              readonly>
            <button
              class="btn btn-outline-secondary"
              type="button"
              (click)="startDatePicker.toggle()">
              <i class="bi bi-calendar3"></i>
            </button>
          </div>
          @if (sessionForm.get('startDate')?.hasError('required') && sessionForm.get('startDate')?.touched) {
            <div class="invalid-feedback d-block">
              Start date is required
            </div>
          }
        </div>

        <div class="mb-3">
          <label for="endDate" class="form-label">End Date</label>
          <div class="input-group">
            <input
              type="text"
              class="form-control"
              id="endDate"
              placeholder="yyyy-mm-dd"
              formControlName="endDate"
              ngbDatepicker
              #endDatePicker="ngbDatepicker"
              [class.is-invalid]="(sessionForm.get('endDate')?.hasError('required') || sessionForm.hasError('dateRange')) && sessionForm.get('endDate')?.touched"
              readonly>
            <button
              class="btn btn-outline-secondary"
              type="button"
              (click)="endDatePicker.toggle()">
              <i class="bi bi-calendar3"></i>
            </button>
          </div>
          @if (sessionForm.get('endDate')?.hasError('required') && sessionForm.get('endDate')?.touched) {
            <div class="invalid-feedback d-block">
              End date is required
            </div>
          }
          @if (sessionForm.hasError('dateRange') && sessionForm.get('endDate')?.touched) {
            <div class="invalid-feedback d-block">
              End date must be after start date
            </div>
          }
        </div>

        <div class="mb-3">
          <div class="form-check">
            <input
              type="checkbox"
              class="form-check-input"
              id="isCurrent"
              formControlName="isCurrent">
            <label class="form-check-label" for="isCurrent">
              Set as current session
            </label>
          </div>
        </div>
      </form>
    </div>

    <div class="modal-footer">
      <button type="button" class="btn btn-secondary" (click)="onCancel()">Cancel</button>
      <button
        type="button"
        class="btn btn-primary"
        (click)="onSubmit()"
        [disabled]="sessionForm.invalid || store.isLoading()">
        {{ isEditMode ? 'Update' : 'Create' }}
      </button>
    </div>
  `,
  styles: [`
    .session-form {
      display: flex;
      flex-direction: column;
      min-width: 400px;
    }

    .modal-body {
      overflow: visible;
    }

    .input-group .btn {
      border-color: #ced4da;
    }

    .input-group .btn:hover {
      background-color: #e9ecef;
    }

    .form-label {
      font-weight: 500;
    }
  `]
})
export class AcademicSessionFormComponent implements OnInit {
  protected readonly store = inject(AcademicSessionStore);
  private readonly fb = inject(FormBuilder);
  public readonly activeModal = inject(NgbActiveModal);

  @Input() mode: 'create' | 'edit' = 'create';
  @Input() session?: AcademicSessionResponse;

  sessionForm!: FormGroup;
  isEditMode = false;

  ngOnInit(): void {
    this.isEditMode = this.mode === 'edit';
    this.initializeForm();
  }

  private initializeForm(): void {
    this.sessionForm = this.fb.group({
      name: [this.session?.name || '', [Validators.required]],
      startDate: [this.session?.startDate ? this.dateToNgbDateStruct(new Date(this.session.startDate)) : null, [Validators.required]],
      endDate: [this.session?.endDate ? this.dateToNgbDateStruct(new Date(this.session.endDate)) : null, [Validators.required]],
      isCurrent: [this.session?.isCurrent || false]
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

    if (startDate && endDate) {
      const start = new Date(startDate.year, startDate.month - 1, startDate.day);
      const end = new Date(endDate.year, endDate.month - 1, endDate.day);

      if (start >= end) {
        return { dateRange: true };
      }
    }

    return null;
  }

  onSubmit(): void {
    if (this.sessionForm.valid) {
      const formValue = this.sessionForm.value;

      // Convert NgbDateStruct dates to ISO string format
      const sessionData = {
        name: formValue.name,
        startDate: this.ngbDateStructToISOString(formValue.startDate),
        endDate: this.ngbDateStructToISOString(formValue.endDate),
        isCurrent: formValue.isCurrent
      };

      if (this.isEditMode && this.session) {
        this.store.updateSession({ id: this.session.id, data: sessionData });
      } else {
        this.store.createSession(sessionData);
      }

      this.activeModal.close(true);
    }
  }

  onCancel(): void {
    this.activeModal.dismiss();
  }

  /**
   * Convert Date to NgbDateStruct
   */
  private dateToNgbDateStruct(date: Date): NgbDateStruct {
    return {
      year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate()
    };
  }

  /**
   * Convert NgbDateStruct to ISO string (YYYY-MM-DD)
   */
  private ngbDateStructToISOString(date: NgbDateStruct): string {
    const year = date.year;
    const month = String(date.month).padStart(2, '0');
    const day = String(date.day).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
