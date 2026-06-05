package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.umb.hotelmanager.dto.BookingResponseDto;
import sk.umb.hotelmanager.entity.Booking;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.repository.BookingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public List<BookingResponseDto> getBookingsForUser(User user) {

        // ADMIN → všetko
        if (user.getRole().name().equals("ADMIN")) {
            return bookingRepository.findAll()
                    .stream()
                    .map(this::mapToResponseDto)
                    .toList();
        }

        // MANAGER → všetky rezervácie z jeho hotelov
        if (user.getRole().name().equals("MANAGER")) {
            return bookingRepository.findByRoomHotelManagerId(user.getId())
                    .stream()
                    .map(this::mapToResponseDto)
                    .toList();
        }

        // USER → iba vlastné
        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    private BookingResponseDto mapToResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .roomId(booking.getRoom().getId())
                .fromDate(booking.getFromDate())
                .toDate(booking.getToDate())
                .guestName(booking.getGuestName())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .note(booking.getNote())
                .build();
    }
}