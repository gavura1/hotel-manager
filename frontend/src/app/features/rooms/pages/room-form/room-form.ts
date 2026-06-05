import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

import { RoomService } from '../../services/room.service';
import { ROOM_TYPE, ROOM_STATUS } from '../../models/room.models';

@Component({
  selector: 'app-room-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    RouterLink,
  ],
  templateUrl: './room-form.html',
  styleUrl: './room-form.css',
})
export class RoomForm implements OnInit {
  roomForm: FormGroup;
  isEditMode = false;
  hotelId!: number;
  private roomId: number | null = null;

  roomTypeOptions = Object.entries(ROOM_TYPE);
  roomStatusOptions = Object.entries(ROOM_STATUS);

  constructor(
    private fb: FormBuilder,
    private roomService: RoomService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.roomForm = this.fb.group({
      roomNumber: ['', Validators.required],
      roomType: ['', Validators.required],
      roomStatus: ['', Validators.required],
      pricePerNight: [null, [Validators.required, Validators.min(0)]],
      capacity: [null, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    this.hotelId = +this.route.snapshot.paramMap.get('hotelId')!;

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.roomId = +id;

      this.roomService.getRoomById(this.roomId).subscribe({
        next: (room) => {
          this.roomForm.patchValue({
            roomNumber: room.roomNumber,
            roomType: room.roomType,
            roomStatus: room.roomStatus,
            pricePerNight: room.pricePerNight,
            capacity: room.capacity,
          });
        },
        error: (err) => console.error('Chyba pri načítaní izby:', err),
      });
    }
  }

  onSubmit(): void {
    if (this.roomForm.invalid) return;

    const roomData = {
      ...this.roomForm.value,
      hotelId: this.hotelId,
    };

    if (this.isEditMode && this.roomId) {
      this.roomService.updateRoom(this.roomId, roomData).subscribe({
        next: () => this.router.navigate(['/izby', this.hotelId]),
        error: (err) => console.error('Chyba pri úprave izby:', err),
      });
    } else {
      this.roomService.createRoom(roomData).subscribe({
        next: () => this.router.navigate(['/izby', this.hotelId]),
        error: (err) => console.error('Chyba pri vytváraní izby:', err),
      });
    }
  }
}
