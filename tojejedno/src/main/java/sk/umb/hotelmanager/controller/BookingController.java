package sk.umb.hotelmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.umb.hotelmanager.dto.BookingRequestDto;
import sk.umb.hotelmanager.dto.BookingResponseDto;
import sk.umb.hotelmanager.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/rezervacie")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByHotel(@PathVariable Long hotelId) {

        return ResponseEntity.ok(bookingService.getBookingsByHotel(hotelId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto requestDto) {
        return ResponseEntity.ok(bookingService.createBooking(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDto> updateBooking(@PathVariable Long id, @RequestBody BookingRequestDto requestDto) {
        return ResponseEntity.ok(bookingService.updateBooking(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponseDto> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

}
