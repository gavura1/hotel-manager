import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Room } from '../models/room.models';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  private apiUrl = `${environment.apiUrl}/api/izby`;

  constructor(private http: HttpClient) {}

  getRoomsByHotel(hotelId: number): Observable<Room[]> {
    return this.http.get<Room[]>(`http://localhost:8080/api/izby/hotel/${hotelId}`);
  }

  getRoomById(id: number): Observable<Room> {
    return this.http.get<Room>(`${this.apiUrl}/${id}`);
  }

  createRoom(room: Room): Observable<Room> {
    return this.http.post<Room>(this.apiUrl, room);
  }

  updateRoom(id: number, room: Room): Observable<Room> {
    return this.http.put<Room>(`${this.apiUrl}/${id}`, room);
  }

  deleteRoom(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
