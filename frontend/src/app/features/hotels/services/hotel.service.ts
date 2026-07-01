import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { Hotel } from '../models/hotel.model';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class HotelService {
  private apiUrl = `${environment.apiUrl}/api/hotely`;

  constructor(private http: HttpClient) {}

  getHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(this.apiUrl);
  }

  getHotelById(id: number): Observable<Hotel> {
    return this.http.get<Hotel>(`${this.apiUrl}/${id}`);
  }

  createHotel(hotel: Hotel): Observable<Hotel> {
    return this.http.post<Hotel>(`${this.apiUrl}`, hotel);
  }

  updateHotel(id: number, hotel: Hotel): Observable<Hotel> {
    return this.http.put<Hotel>(`${this.apiUrl}/${id}`, hotel);
  }

  deleteHotel(id:number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
