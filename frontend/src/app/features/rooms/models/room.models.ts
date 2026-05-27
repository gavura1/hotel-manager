export interface Room {
  id?: number;
  roomNumber: string;
  roomType: 'APARTMENT' | 'TWO_BED' | 'THREE_BED';
  roomStatus: 'AVAILABLE' | 'OCCUPIED' | 'OUT_OF_SERVICE';
  pricePerNight: number;
  capacity: number;
  hotelId: number;
}

export const ROOM_TYPE: Record<Room['roomType'], string> = {
  'APARTMENT': 'Apartmán',
  'TWO_BED': 'Dvojlôžková',
  'THREE_BED': 'Trojlôžková',
};

export const ROOM_STATUS: Record<Room['roomStatus'], string> = {
  'AVAILABLE': 'Dostupná',
  'OCCUPIED': 'Obsadená',
  'OUT_OF_SERVICE': 'Mimo prevádzky',
};
