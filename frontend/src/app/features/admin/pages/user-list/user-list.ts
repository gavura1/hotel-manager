import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule,
  ],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserList implements OnInit {
  dataSource = new MatTableDataSource<any>([]);
  hotels: any[] = [];
  displayedColumns: string[] = ['id', 'name', 'email', 'role', 'hotels', 'actions'];
  roles = ['ADMIN', 'MANAGER', 'USER'];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadHotels();
  }

  loadUsers(): void {
    this.http.get<any[]>('http://localhost:8080/api/admin/pouzivatelia').subscribe({
      next: (data: any[]) => (this.dataSource.data = data),
      error: (err: any) => console.error(err),
    });
  }

  loadHotels(): void {
    this.http.get<any[]>('http://localhost:8080/api/hotely').subscribe({
      next: (data: any[]) => (this.hotels = data),
      error: (err: any) => console.error(err),
    });
  }

  updateRole(user: any): void {
    this.http
      .put(`http://localhost:8080/api/admin/pouzivatelia/${user.id}/rola`, { role: user.role })
      .subscribe({
        next: () => this.loadUsers(),
        error: (err: any) => console.error(err),
      });
  }

  updateHotels(user: any): void {
    this.http
      .put(`http://localhost:8080/api/admin/pouzivatelia/${user.id}/hotely`, {
        hotelIds: user.hotelIds,
      })
      .subscribe({
        next: () => this.loadUsers(),
        error: (err: any) => console.error(err),
      });
  }

  getHotelName(id: number): string {
    return this.hotels.find((h) => h.id === id)?.name ?? '';
  }
}
