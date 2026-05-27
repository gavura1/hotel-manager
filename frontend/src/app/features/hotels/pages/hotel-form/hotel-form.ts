import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { HotelService } from '../../services/hotel.service';

@Component({
  selector: 'app-hotel-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './hotel-form.html',
  styleUrl: './hotel-form.css',
})
export class HotelForm implements OnInit {
  hotelForm: FormGroup;
  isEditMode = false;
  private hotelId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private hotelService: HotelService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.hotelForm = this.fb.group({
      name: ['', Validators.required],
      address: ['', Validators.required],
      description: [''],
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode = true;
      this.hotelId = +id;

      this.hotelService.getHotelById(this.hotelId).subscribe({
        next: (hotel: any) => {
          this.hotelForm.patchValue({
            name: hotel.name,
            address: hotel.address,
            description: hotel.description,
          });
        },
        error: (err: any) => console.error('Chyba pri načítaní hotela:', err),
      });
    }
  }

  onSubmit(): void {
    if (this.hotelForm.invalid) return;

    const hotelData = this.hotelForm.value;

    if (this.isEditMode && this.hotelId) {
      this.hotelService.updateHotel(this.hotelId, hotelData).subscribe({
        next: () => this.router.navigate(['/hotely']),
        error: (err: any) => console.error('Chyba pri úprave hotela:', err),
      });
    } else {
      this.hotelService.createHotel(hotelData).subscribe({
        next: () => this.router.navigate(['/hotely']),
        error: (err: any) => console.error('Chyba pri vytváraní hotela:', err),
      });
    }
  }
}
