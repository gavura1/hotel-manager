import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';

import { BookingService } from '../../services/booking.service';
import { Booking } from '../../models/booking.model';
import { AuthService } from '../../../../auth/auth.service';

@Component({
  selector: 'app-booking-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, RouterLink],
  templateUrl: './booking-list.html',
  styleUrl: './booking-list.css',
})
export class BookingList implements OnInit {
  dataSource = new MatTableDataSource<Booking>([]);
  displayedColumns: string[] = [
    'id',
    'guestName',
    'fromDate',
    'toDate',
    'status',
    'totalPrice',
    'actions',
  ];
  currentUserId: number = 0;
  allBookings: Booking[] = [];
  activeFilter: string = 'ALL';

  constructor(
    private bookingService: BookingService,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    if (this.authService.isAdmin()) {
      this.bookingService.getAllBookings().subscribe({
        next: (data) => {
          this.allBookings = data;
          this.applyFilter(this.activeFilter);
        },
        error: (err) => console.error(err),
      });
    } else {
      this.authService.getMe().subscribe({
        next: (user: any) => {
          this.currentUserId = user.id;
          this.loadBookings(user.id);
        },
        error: (err) => console.error(err),
      });
    }
  }

  loadBookings(userId: number): void {
    this.bookingService.getBookingsByUser(userId).subscribe({
      next: (data) => {
        this.allBookings = data;
        this.applyFilter(this.activeFilter);
      },
      error: (err) => console.error(err),
    });
  }

  applyFilter(filter: string): void {
    this.activeFilter = filter;
    if (filter === 'ALL') {
      this.dataSource.data = this.allBookings;
    } else {
      this.dataSource.data = this.allBookings.filter((b) => b.status === filter);
    }
  }

  cancelBooking(id: number): void {
    if (!confirm('Naozaj chceš zrušiť túto rezerváciu?')) return;

    this.bookingService.cancelBooking(id).subscribe({
      next: () => this.loadBookings(this.currentUserId),
      error: (err) => console.error(err),
    });
  }
}
