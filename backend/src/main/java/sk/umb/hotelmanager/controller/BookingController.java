package sk.umb.hotelmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sk.umb.hotelmanager.dto.BookingResponseDto;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.repository.UserRepository;
import sk.umb.hotelmanager.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/rezervacie")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAll(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        return ResponseEntity.ok(
                bookingService.getBookingsForUser(user)
        );
    }
}