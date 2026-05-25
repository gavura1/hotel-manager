package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.umb.hotelmanager.dto.BookingResponseDto;
import sk.umb.hotelmanager.entity.Booking;
import sk.umb.hotelmanager.repository.BookingRepository;
import sk.umb.hotelmanager.repository.HotelRepository;
import sk.umb.hotelmanager.repository.RoomRepository;
import sk.umb.hotelmanager.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public List<BookingResponseDto> getBookingsByHotel(Long hotelId) {

        return bookingRepository.findByRoomHotelId(hotelId).stream().map(this::mapToResponseDto).toList();
    }


    public List<BookingResponseDto> getBookingsByUser(Long userId) {

        return bookingRepository.findByUserId(userId).stream().map(this::mapToResponseDto).toList();
    }


    public BookingResponseDto getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException
                ("Rezervácia s  týmto id sa nenašla"));

        return mapToResponseDto(booking);
    }





    private BookingResponseDto mapToResponseDto(Booking booking) {
        return BookingResponseDto.builder().id(booking.getId()).roomId(booking.getRoom().getId())
                .fromDate(booking.getFromDate()).toDate(booking.getToDate()).guestName(booking.getGuestName())
                .totalPrice(booking.getTotalPrice()).status(booking.getStatus()).createdAt(booking.getCreatedAt())
                .note(booking.getNote()).build();
    }
}
