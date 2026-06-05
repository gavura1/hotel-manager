export interface Booking {
  id: number;
  roomId: number;
  fromDate: Date;
  toDate: Date;
  guestName: string;
  totalPrice: number;
  status: 'CONFIRMED' | 'CANCELLED' | 'COMPLETED';
  createdAt: Date;
  hotelName: string;
  note: string;
}
