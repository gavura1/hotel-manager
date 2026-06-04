package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sk.umb.hotelmanager.dto.RoomRequestDto;
import sk.umb.hotelmanager.dto.RoomResponseDto;
import sk.umb.hotelmanager.entity.Hotel;
import sk.umb.hotelmanager.entity.Room;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.repository.HotelRepository;
import sk.umb.hotelmanager.repository.RoomRepository;
import sk.umb.hotelmanager.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public List<RoomResponseDto> getAllRooms() {
        return roomRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public RoomResponseDto getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Izba podľa id nebola nájdená"));
        return mapToResponseDto(room);
    }

    public List<RoomResponseDto> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelId(hotelId).stream().map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public RoomResponseDto createRoom(RoomRequestDto requestDto, String email) {
        Hotel hotel = hotelRepository.findById(requestDto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel nebol nájdený"));

        checkManagerAccess(email, hotel);

        Room room = Room.builder()
                .roomNumber(requestDto.getRoomNumber())
                .roomType(requestDto.getRoomType())
                .roomStatus(requestDto.getRoomStatus())
                .pricePerNight(requestDto.getPricePerNight())
                .capacity(requestDto.getCapacity())
                .hotel(hotel)
                .build();

        return mapToResponseDto(roomRepository.save(room));
    }

    public RoomResponseDto updateRoom(Long id, RoomRequestDto requestDto, String email) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Izba nebola nájdená"));
        Hotel hotel = hotelRepository.findById(requestDto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel nebol nájdený"));

        checkManagerAccess(email, hotel);

        room.setRoomNumber(requestDto.getRoomNumber());
        room.setRoomType(requestDto.getRoomType());
        room.setRoomStatus(requestDto.getRoomStatus());
        room.setPricePerNight(requestDto.getPricePerNight());
        room.setCapacity(requestDto.getCapacity());
        room.setHotel(hotel);

        return mapToResponseDto(roomRepository.save(room));
    }

    public void deleteRoom(Long id, String email) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Izba nebola nájdená"));

        checkManagerAccess(email, room.getHotel());

        roomRepository.deleteById(id);
    }

    private void checkManagerAccess(String email, Hotel hotel) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Používateľ nebol nájdený"));

        if (user.getRole().name().equals("ADMIN")) return;

        if (hotel.getManager() == null || !hotel.getManager().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Nemáte oprávnenie spravovať izby tohto hotela");
        }
    }

    private RoomResponseDto mapToResponseDto(Room room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .roomStatus(room.getRoomStatus())
                .pricePerNight(room.getPricePerNight())
                .capacity(room.getCapacity())
                .hotelId(room.getHotel().getId())
                .build();
    }
}