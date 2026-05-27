package sk.umb.hotelmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.umb.hotelmanager.dto.HotelRequestDto;
import sk.umb.hotelmanager.dto.HotelResponseDto;
import sk.umb.hotelmanager.service.HotelService;

import java.util.List;

@RestController
@RequestMapping("api/hotely")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public List<HotelResponseDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelResponseDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public HotelResponseDto createHotel(@RequestBody HotelRequestDto RequestDto) {
        return hotelService.createHotel(RequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public HotelResponseDto updateHotel(@PathVariable Long id, @RequestBody HotelRequestDto RequestDto) {
        return hotelService.updateHotel(id, RequestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteHotelById(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }
}
