import { computed, inject } from '@angular/core';
import { signalStore, withState, withComputed, withMethods, patchState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, switchMap, tap, catchError, of } from 'rxjs';
import { tapResponse } from '@ngrx/operators';
import { AcademicSessionResponse } from '../models/academic-session.model';
import { AcademicSessionService } from '../services/academic-session.service';

/**
 * State interface for Academic Session Store
 */
interface AcademicSessionState {
  sessions: AcademicSessionResponse[];
  selectedSession: AcademicSessionResponse | null;
  currentSession: AcademicSessionResponse | null;
  isLoading: boolean;
  error: string | null;
}

/**
 * Initial state
 */
const initialState: AcademicSessionState = {
  sessions: [],
  selectedSession: null,
  currentSession: null,
  isLoading: false,
  error: null
};

/**
 * NgRx Signal Store for Academic Session Management
 * Requirement: SET-01
 */
export const AcademicSessionStore = signalStore(
  { providedIn: 'root' },
  withState(initialState),
  withComputed(({ sessions, currentSession }) => ({
    sessionCount: computed(() => sessions().length),
    hasCurrentSession: computed(() => currentSession() !== null)
  })),
  withMethods((store, academicSessionService = inject(AcademicSessionService)) => ({
    /**
     * Load all academic sessions
     */
    loadSessions: rxMethod<void>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null })),
        switchMap(() =>
          academicSessionService.getAllSessions().pipe(
            tapResponse({
              next: (sessions) => patchState(store, { sessions, isLoading: false }),
              error: (error: Error) => patchState(store, { error: error.message, isLoading: false })
            })
          )
        )
      )
    ),

    /**
     * Load current academic session
     */
    loadCurrentSession: rxMethod<void>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null })),
        switchMap(() =>
          academicSessionService.getCurrentSession().pipe(
            tapResponse({
              next: (currentSession) => patchState(store, { currentSession, isLoading: false }),
              error: (error: Error) => patchState(store, { error: error.message, isLoading: false })
            }),
            catchError(() => {
              patchState(store, { currentSession: null, isLoading: false });
              return of(null);
            })
          )
        )
      )
    ),

    /**
     * Create a new academic session
     */
    createSession: rxMethod<{ name: string; startDate: string; endDate: string; isCurrent?: boolean }>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null })),
        switchMap((request) =>
          academicSessionService.createSession(request).pipe(
            tapResponse({
              next: (newSession) => {
                const sessions = [...store.sessions(), newSession];
                const updates: Partial<AcademicSessionState> = { sessions, isLoading: false };
                if (newSession.isCurrent) {
                  updates.currentSession = newSession;
                }
                patchState(store, updates);
              },
              error: (error: Error) => patchState(store, { error: error.message, isLoading: false })
            })
          )
        )
      )
    ),

    /**
     * Update an academic session
     */
    updateSession: rxMethod<{ id: number; data: { name: string; startDate: string; endDate: string; isCurrent?: boolean } }>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null })),
        switchMap(({ id, data }) =>
          academicSessionService.updateSession(id, data).pipe(
            tapResponse({
              next: (updatedSession) => {
                const sessions = store.sessions().map(s => s.id === id ? updatedSession : s);
                const updates: Partial<AcademicSessionState> = { sessions, isLoading: false };
                if (store.selectedSession()?.id === id) {
                  updates.selectedSession = updatedSession;
                }
                if (updatedSession.isCurrent) {
                  updates.currentSession = updatedSession;
                }
                patchState(store, updates);
              },
              error: (error: Error) => patchState(store, { error: error.message, isLoading: false })
            })
          )
        )
      )
    ),

    /**
     * Set a session as current
     */
    setCurrentSession: rxMethod<number>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null })),
        switchMap((id) =>
          academicSessionService.setCurrentSession(id).pipe(
            tapResponse({
              next: (currentSession) => {
                // Update all sessions: unset previous current and set new current
                const sessions = store.sessions().map(s => ({
                  ...s,
                  isCurrent: s.id === id
                }));
                patchState(store, { sessions, currentSession, isLoading: false });
              },
              error: (error: Error) => patchState(store, { error: error.message, isLoading: false })
            })
          )
        )
      )
    ),

    /**
     * Delete an academic session
     */
    deleteSession: rxMethod<number>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null })),
        switchMap((id) =>
          academicSessionService.deleteSession(id).pipe(
            tapResponse({
              next: () => {
                const sessions = store.sessions().filter(s => s.id !== id);
                const updates: Partial<AcademicSessionState> = { sessions, isLoading: false };
                if (store.selectedSession()?.id === id) {
                  updates.selectedSession = null;
                }
                if (store.currentSession()?.id === id) {
                  updates.currentSession = null;
                }
                patchState(store, updates);
              },
              error: (error: Error) => patchState(store, { error: error.message, isLoading: false })
            })
          )
        )
      )
    ),

    /**
     * Select a session
     */
    selectSession(session: AcademicSessionResponse | null): void {
      patchState(store, { selectedSession: session });
    },

    /**
     * Clear error
     */
    clearError(): void {
      patchState(store, { error: null });
    }
  }))
);
