import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';

import { RouterLink } from '@angular/router';

import { HotelService } from '../../services/hotel.service';
import { Hotel } from '../../models/hotel.model';

@Component({
  selector: 'app-hotel-list',
  imports: [CommonModule, MatTableModule, MatButtonModule, RouterLink],
  templateUrl: './hotel-list.html',
  styleUrl: './hotel-list.css',
})
export class HotelList implements OnInit {
  hotels: Hotel[] = [];

  displayedColumns: string[] = ['id', 'name', 'address', 'description', 'actions'];

  constructor(private hotelService: HotelService) {}

  ngOnInit(): void {
    this.loadHotels();
  }

  loadHotels(): void {
    this.hotelService.getHotels().subscribe({
      next: (data) => {
        this.hotels = data;
      },

      error: (err) => {
        console.error(err);
      },
    });
  }

  deleteHotel(id: number): void {
    this.hotelService.deleteHotel(id).subscribe({
      next: () => {
        this.loadHotels();
      },

      error: (err) => {
        console.error(err);
      },
    });
  }
}
