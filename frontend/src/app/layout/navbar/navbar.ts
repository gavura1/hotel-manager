import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, MatButtonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  userEmail: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.userEmail = this.authService.getCurrentUserEmail();
  }

  logout(): void {
    this.authService.logout();
  }
}
