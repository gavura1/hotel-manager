package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.umb.hotelmanager.dto.HotelRequestDto;
import sk.umb.hotelmanager.dto.HotelResponseDto;
import sk.umb.hotelmanager.entity.Hotel;
import sk.umb.hotelmanager.repository.HotelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<HotelResponseDto> getAllHotels() {

        return hotelRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public HotelResponseDto getHotelById(Long id) {

        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel s týmto id sa nenašiel"));

        return mapToResponseDto(hotel);
    }

    public HotelResponseDto createHotel(HotelRequestDto RequestDto) {
        Hotel hotel = Hotel.builder().name(RequestDto.getName())
                .address(RequestDto.getAddress()).description(RequestDto.getDescription()).build();

        Hotel savedHotel = hotelRepository.save(hotel);

        return mapToResponseDto(savedHotel);
    }

    public HotelResponseDto updateHotel(Long id, HotelRequestDto RequestDto) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel s týmto id sa nenašiel"));

        hotel.setName(RequestDto.getName());
        hotel.setAddress(RequestDto.getAddress());
        hotel.setDescription(RequestDto.getDescription());

        Hotel updatedHotel = hotelRepository.save(hotel);

        return mapToResponseDto(updatedHotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    private HotelResponseDto mapToResponseDto(Hotel hotel) {
        return HotelResponseDto.builder().id(hotel.getId()).name(hotel.getName()).address(hotel.getAddress())
                .description(hotel.getDescription()).managerId(hotel.getManager() != null ? hotel.getManager().getId() : null)
                .managerName(hotel.getManager() != null ? hotel.getManager().getName() : null).build();
    }
}
