package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.umb.hotelmanager.dto.RoomRequestDto;
import sk.umb.hotelmanager.dto.RoomResponseDto;
import sk.umb.hotelmanager.entity.Hotel;
import sk.umb.hotelmanager.entity.Room;
import sk.umb.hotelmanager.repository.HotelRepository;
import sk.umb.hotelmanager.repository.RoomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public List<RoomResponseDto> getAllRooms() {
        return roomRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public RoomResponseDto getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Izba podľa id nebola nájdená"));
        return mapToResponseDto(room);
    }

    public RoomResponseDto createRoom(RoomRequestDto requestDto) {
        Hotel hotel = hotelRepository.findById(requestDto.getHotelId()).orElseThrow(() -> new RuntimeException("Hotel nebol nájdený"));
        Room room = Room.builder().roomNumber(requestDto.getRoomNumber()).roomType(requestDto.getRoomType())
                .roomStatus(requestDto.getRoomStatus()).pricePerNight(requestDto.getPricePerNight())
                .capacity(requestDto.getCapacity()).hotel(hotel).build();

        Room savedRoom = roomRepository.save(room);

        return mapToResponseDto(savedRoom);
    }

    public RoomResponseDto updateRoom(Long id, RoomRequestDto requestDto) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Izba nebola nájdená"));
        Hotel hotel = hotelRepository.findById(requestDto.getHotelId()).orElseThrow(() -> new RuntimeException("Hotel nebol nájdený"));

        room.setRoomNumber(requestDto.getRoomNumber());
        room.setRoomType(requestDto.getRoomType());
        room.setRoomStatus(requestDto.getRoomStatus());
        room.setPricePerNight(requestDto.getPricePerNight());
        room.setCapacity(requestDto.getCapacity());
        room.setHotel(hotel);

        Room updatedRoom = roomRepository.save(room);
        return mapToResponseDto(updatedRoom);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    private RoomResponseDto mapToResponseDto(Room room) {
        return RoomResponseDto.builder().id(room.getId()).roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType()).roomStatus(room.getRoomStatus()).pricePerNight(room.getPricePerNight())
                .capacity(room.getCapacity()).hotelId(room.getHotel().getId()).build();
    }
}
