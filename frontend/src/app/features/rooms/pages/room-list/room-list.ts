import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, ActivatedRoute, Router } from '@angular/router';

import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { HttpClient } from '@angular/common/http';

import { HotelService } from '../../../hotels/services/hotel.service';
import { RoomService } from '../../services/room.service';
import { AuthService } from '../../../../auth/auth.service';
import { Hotel } from '../../../hotels/models/hotel.model';
import { Room, ROOM_TYPE, ROOM_STATUS } from '../../models/room.models';

@Component({
  selector: 'app-room-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, RouterLink],
  templateUrl: './room-list.html',
  styleUrl: './room-list.css',
})
export class RoomList implements OnInit {
  hotelId: number | null = null;
  isAdminOrManager = false;
  isManager = false;

  hotelDataSource = new MatTableDataSource<Hotel>([]);
  hotelColumns: string[] = ['id', 'name', 'address', 'actions'];

  roomDataSource = new MatTableDataSource<Room>([]);
  roomColumns: string[] = [
    'roomNumber',
    'roomType',
    'roomStatus',
    'pricePerNight',
    'capacity',
    'actions',
  ];

  ROOM_TYPE = ROOM_TYPE;
  ROOM_STATUS = ROOM_STATUS;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hotelService: HotelService,
    private roomService: RoomService,
    private authService: AuthService,
    private http: HttpClient,
  ) {
    this.isAdminOrManager = this.authService.isAdminOrManager();
    this.isManager = this.authService.isManager();
    if (!this.isAdminOrManager) {
      this.roomColumns = ['roomNumber', 'roomType', 'roomStatus', 'pricePerNight', 'capacity'];
    }
  }

  ngOnInit(): void {
    const param = this.route.snapshot.paramMap.get('hotelId');
    this.hotelId = param ? +param : null;

    if (this.hotelId) {
      this.loadRooms();
    } else {
      this.loadHotels();
    }
  }

  loadHotels(): void {
    this.hotelService.getHotels().subscribe({
      next: (hotels) => {
        if (this.isManager) {
          const email = this.authService.getCurrentUserEmail();
          this.authService.getMe().subscribe({
            next: (user: any) => {
              this.hotelDataSource.data = hotels.filter((h) => h.managerId === user.id);
            },
            error: (err) => console.error(err),
          });
        } else {
          this.hotelDataSource.data = hotels;
        }
      },
      error: (err) => console.error(err),
    });
  }

  loadRooms(): void {
    this.roomService.getRoomsByHotel(this.hotelId!).subscribe({
      next: (data) => (this.roomDataSource.data = data),
      error: (err) => console.error(err),
    });
  }

  deleteRoom(id: number): void {
    if (!confirm('Naozaj chceš vymazať túto izbu?')) return;
    this.roomService.deleteRoom(id).subscribe({
      next: () => this.loadRooms(),
      error: (err) => console.error(err),
    });
  }

  getRoomTypeLabel(type: string): string {
    return ROOM_TYPE[type as Room['roomType']] ?? type;
  }

  getRoomStatusLabel(status: string): string {
    return ROOM_STATUS[status as Room['roomStatus']] ?? status;
  }
}
