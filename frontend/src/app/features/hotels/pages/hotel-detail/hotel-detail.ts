import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';

import { HotelService } from '../../services/hotel.service';
import { RoomService } from '../../../rooms/services/room.service';

@Component({
  selector: 'app-hotel-detail',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatTableModule],
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

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hotelService: HotelService,
    private roomService: RoomService,
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('hotel-detail id:', id);

    this.hotelService.getHotelById(id).subscribe({
      next: (data) => (this.hotel = data),
      error: (err) => console.error(err),
    });

    this.hotelService.getHotelById(id).subscribe({
      next: (data) => {
        console.log('hotel data:', data);
        this.hotel = data;
      },
      error: (err) => console.error(err),
    });
  }

  rezervovat(roomId: number): void {
    this.router.navigate(['/rezervacie/vytvorit'], { queryParams: { roomId } });
  }
}
