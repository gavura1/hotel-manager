import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';

import { BookingService } from '../../services/booking.service';
import { Booking } from '../../models/booking.model';


@Component({
  selector: 'app-booking-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule],
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

  constructor(private bookingService: BookingService) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings(): void {
    this.bookingService.getBookingsByUser(1).subscribe({
      next: (data) => {
        this.dataSource.data = data;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  cancelBooking(id: number): void {
    if(!confirm('Naozaj chceš vymazat túto rezerváciu?')) return;

    this.bookingService.cancelBooking(id).subscribe({
      next: (data) => this.loadBookings(),
      error: (err) => console.error(err),
    })
  }
}
