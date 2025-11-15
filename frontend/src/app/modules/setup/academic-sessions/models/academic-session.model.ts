/**
 * Models for Academic Session Management
 * Requirement: SET-01
 */

export interface AcademicSessionRequest {
  name: string;
  startDate: string; // ISO date string
  endDate: string;   // ISO date string
  isCurrent?: boolean;
}

export interface AcademicSessionResponse {
  id: number;
  name: string;
  startDate: string; // ISO date string
  endDate: string;   // ISO date string
  isCurrent: boolean;
  createdAt: string; // ISO datetime string
  updatedAt: string; // ISO datetime string
}
