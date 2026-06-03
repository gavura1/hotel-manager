import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';

import { BookingService} from '../../services/booking.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-booking-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
  ],
  templateUrl: './booking-form.html',
  styleUrl: './booking-form.css',
})
export class BookingForm implements OnInit {
  bookingForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private bookingService: BookingService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.bookingForm = this.fb.group({
      roomId: ['', Validators.required],
      guestName: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
      note: [''],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.bookingForm.invalid) return;

    this.bookingService.createBooking(this.bookingForm.value).subscribe({
      next: () => this.router.navigate(['/rezervacie']),
      error: (err: any) => console.error(err),
    });
  }
}
