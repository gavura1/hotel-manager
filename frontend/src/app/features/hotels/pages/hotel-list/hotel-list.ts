import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';

import { HotelService } from '../../services/hotel.service';
import { Hotel } from '../../models/hotel.model';
import { AuthService } from '../../../../auth/auth.service';

@Component({
  selector: 'app-hotel-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, RouterLink],
  templateUrl: './hotel-list.html',
  styleUrl: './hotel-list.css',
})
export class HotelList implements OnInit {
  dataSource = new MatTableDataSource<Hotel>([]);
  displayedColumns: string[] = ['id', 'name', 'address', 'description', 'actions'];
  isAdmin = false;

  constructor(
    private hotelService: HotelService,
    private authService: AuthService
  ) {
    this.isAdmin = this.authService.isAdmin();
    if (!this.isAdmin) {
      this.displayedColumns = ['id', 'name', 'address', 'description', 'actions'];
    }
  }

  ngOnInit(): void {
    this.loadHotels();
  }

  loadHotels(): void {
    this.hotelService.getHotels().subscribe({
      next: (data) => {
        this.dataSource.data = data;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  deleteHotel(id: number): void {
    if (!confirm('Naozaj chceš vymazať tento hotel?')) return;

    this.hotelService.deleteHotel(id).subscribe({
      next: () => this.loadHotels(),
      error: (err) => console.error(err),
    });
  }
}
