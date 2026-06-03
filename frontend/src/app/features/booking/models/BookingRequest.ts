export interface BookingRequest {
  roomId: number;
  fromDate: Date;
  toDate: Date;
  guestName: string;
  note: string;
}
