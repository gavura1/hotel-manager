import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';

import { HotelService } from '../../services/hotel.service';
import { RoomService } from '../../../rooms/services/room.service';
import { AuthService } from '../../../../auth/auth.service';

@Component({
  selector: 'app-hotel-detail',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatTableModule, RouterLink],
  templateUrl: './hotel-detail.html',
  styleUrl: './hotel-detail.css',
})
export class HotelDetail implements OnInit {
  hotel: any = {};
  dataSource = new MatTableDataSource<any>([]);
  displayedColumns: string[] = [
    'roomNumber',
    'roomType',
    'capacity',
    'pricePerNight',
    'roomStatus',
    'actions',
  ];
  isAdmin = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hotelService: HotelService,
    private roomService: RoomService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
  ) {
    this.isAdmin = this.authService.isAdmin();
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.hotelService.getHotelById(id).subscribe({
      next: (data) => {
        this.hotel = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err),
    });

    this.roomService.getRoomsByHotel(id).subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err),
    });
  }

  rezervovat(roomId: number): void {
    this.router.navigate(['/rezervacie/vytvorit'], { queryParams: { roomId } });
  }
}
