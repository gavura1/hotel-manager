import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

import { BookingService } from '../../services/booking.service';
import { RoomService } from '../../../rooms/services/room.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-booking-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],
  templateUrl: './booking-form.html',
  styleUrl: './booking-form.css',
})
export class BookingForm implements OnInit {
  bookingForm: FormGroup;
  rooms: any[] = [];

  constructor(
    private fb: FormBuilder,
    private bookingService: BookingService,
    private roomService: RoomService,
    private router: Router,
  ) {
    this.bookingForm = this.fb.group({
      roomId: ['', Validators.required],
      guestName: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
      note: [''],
    });
  }

  ngOnInit(): void {
    this.roomService.getRoomsByHotel(1).subscribe({
      next: (data) => {
        this.rooms = data;
      },
      error: (err) => console.error(err),
    });
  }

  onSubmit(): void {
    if (this.bookingForm.invalid) return;

    this.bookingService.createBooking(this.bookingForm.value).subscribe({
      next: () => this.router.navigate(['/rezervacie']),
      error: (err: any) => console.error(err),
    });
  }
}
