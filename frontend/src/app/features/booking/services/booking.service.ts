import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Booking } from '../models/booking.model';
import { BookingRequest } from '../models/BookingRequest';

@Injectable({ providedIn: 'root' })
export class BookingService {

  private apiUrl = 'http://localhost:8080/api/rezervacie';

  constructor(private http: HttpClient) {}

  // ADMIN / USER / MANAGER (role-based backend)
  getBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(this.apiUrl);
  }

  getBookingsByHotel(hotelId: number): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.apiUrl}/hotel/${hotelId}`);
  }

  getBookingById(id: number): Observable<Booking> {
    return this.http.get<Booking>(`${this.apiUrl}/${id}`);
  }

  createBooking(booking: BookingRequest): Observable<Booking> {
    return this.http.post<Booking>(this.apiUrl, booking);
  }

  cancelBooking(id: number): Observable<Booking> {
    return this.http.delete<Booking>(`${this.apiUrl}/${id}`);
  }

  updateBooking(id: number, booking: BookingRequest): Observable<Booking> {
    return this.http.put<Booking>(`${this.apiUrl}/${id}`, booking);
  }
}
