import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onGoogleLogin(): void {
    window.location.href = `${environment.apiUrl}/oauth2/authorization/google`;
  }

  onSubmit(): void {
    if (this.loginForm.invalid) return;

    this.http.post<{ token: string }>(`${environment.apiUrl}/auth/login`, this.loginForm.value).subscribe({
      next: (response) => {
        this.authService.saveToken(response.token);
        this.router.navigate(['/hotely']);
      },
      error: (err) => {
        this.errorMessage = 'Nesprávne prihlasovacie údaje';
      },
    });
  }
}
