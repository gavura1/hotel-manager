package sk.umb.hotelmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.umb.hotelmanager.dto.RoomRequestDto;
import sk.umb.hotelmanager.dto.RoomResponseDto;
import sk.umb.hotelmanager.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/izby")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomResponseDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomResponseDto getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponseDto> getRoomsByHotel(@PathVariable Long hotelId) {
        return roomService.getRoomsByHotel(hotelId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public RoomResponseDto createRoom(@RequestBody RoomRequestDto requestDto, Authentication authentication) {
        return roomService.createRoom(requestDto, authentication.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public RoomResponseDto updateRoom(@PathVariable Long id, @RequestBody RoomRequestDto requestDto, Authentication authentication) {
        return roomService.updateRoom(id, requestDto, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void deleteRoom(@PathVariable Long id, Authentication authentication) {
        roomService.deleteRoom(id, authentication.getName());
    }
}
