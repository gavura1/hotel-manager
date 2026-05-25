package sk.umb.hotelmanager.controller;

import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public RoomResponseDto createRoom(@RequestBody RoomRequestDto requestDto) {
        return roomService.createRoom(requestDto);
    }

    @PutMapping("/{id}")
    public RoomResponseDto updateRoom(@PathVariable Long id, @RequestBody RoomRequestDto requestDto) {
        return roomService.updateRoom(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
