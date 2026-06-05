import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private tokenKey = 'jwt_token';

  constructor(
    private router: Router,
    private http: HttpClient,
  ) {}

  // =========================
  // TOKEN MANAGEMENT
  // =========================

  saveToken(token: string): void {
    if (!token) return;
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !this.isTokenExpired();
  }

  // =========================
  // USER INFO (JWT decode)
  // =========================

  private decodeToken(): any | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch {
      return null;
    }
  }

  getCurrentUserEmail(): string | null {
    const payload = this.decodeToken();
    return payload?.sub ?? null;
  }

  getCurrentUserRole(): string | null {
    const payload = this.decodeToken();
    return payload?.role ?? null;
  }

  // =========================
  // ROLE HELPERS
  // =========================

  isAdmin(): boolean {
    return this.getCurrentUserRole() === 'ADMIN';
  }

  isManager(): boolean {
    return this.getCurrentUserRole() === 'MANAGER';
  }

  isAdminOrManager(): boolean {
    return this.isAdmin() || this.isManager();
  }

  // =========================
  // TOKEN VALIDATION
  // =========================

  isTokenExpired(): boolean {
    const payload = this.decodeToken();
    if (!payload?.exp) return true;

    return payload.exp * 1000 < Date.now();
  }

  // =========================
  // API CALLS
  // =========================

  getMe(): Observable<any> {
    return this.http.get('http://localhost:8080/auth/me');
  }
}
