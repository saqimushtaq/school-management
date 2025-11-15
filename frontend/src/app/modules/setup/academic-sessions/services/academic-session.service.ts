import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { AcademicSessionRequest, AcademicSessionResponse } from '../models/academic-session.model';

/**
 * Service for Academic Session API communication
 * Requirement: SET-01
 */
@Injectable({
  providedIn: 'root'
})
export class AcademicSessionService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/setup/academic-sessions`;

  /**
   * Create a new academic session
   */
  createSession(request: AcademicSessionRequest): Observable<AcademicSessionResponse> {
    return this.http.post<AcademicSessionResponse>(this.baseUrl, request);
  }

  /**
   * Get all academic sessions
   */
  getAllSessions(): Observable<AcademicSessionResponse[]> {
    return this.http.get<AcademicSessionResponse[]>(this.baseUrl);
  }

  /**
   * Get academic session by ID
   */
  getSessionById(id: number): Observable<AcademicSessionResponse> {
    return this.http.get<AcademicSessionResponse>(`${this.baseUrl}/${id}`);
  }

  /**
   * Get the current academic session
   */
  getCurrentSession(): Observable<AcademicSessionResponse> {
    return this.http.get<AcademicSessionResponse>(`${this.baseUrl}/current`);
  }

  /**
   * Update an academic session
   */
  updateSession(id: number, request: AcademicSessionRequest): Observable<AcademicSessionResponse> {
    return this.http.put<AcademicSessionResponse>(`${this.baseUrl}/${id}`, request);
  }

  /**
   * Set a session as current
   */
  setCurrentSession(id: number): Observable<AcademicSessionResponse> {
    return this.http.patch<AcademicSessionResponse>(`${this.baseUrl}/${id}/set-current`, {});
  }

  /**
   * Delete an academic session
   */
  deleteSession(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
