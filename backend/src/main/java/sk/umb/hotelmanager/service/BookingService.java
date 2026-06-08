package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.umb.hotelmanager.dto.BookingRequestDto;
import sk.umb.hotelmanager.dto.BookingResponseDto;
import sk.umb.hotelmanager.entity.Booking;
import sk.umb.hotelmanager.entity.Room;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.enums.BookingStatus;
import sk.umb.hotelmanager.repository.BookingRepository;
import sk.umb.hotelmanager.repository.RoomRepository;
import sk.umb.hotelmanager.repository.UserRepository;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public List<BookingResponseDto> getBookingsByHotel(Long hotelId) {
        return bookingRepository.findByRoomHotelId(hotelId).stream().map(this::mapToResponseDto).toList();
    }

    public List<BookingResponseDto> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream().map(this::mapToResponseDto).toList();
    }

    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervácia s týmto id sa nenašla"));
        return mapToResponseDto(booking);
    }

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto requestDto, String email) {
        Room room = roomRepository.findById(requestDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Izba s daným id sa nenašla"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Používateľ nebol nájdený"));

        List<Room> availableRooms = roomRepository.findAvailableRooms(
                room.getHotel().getId(),
                requestDto.getFromDate(),
                requestDto.getToDate()
        );

        if (availableRooms.stream().noneMatch(r -> r.getId().equals(room.getId()))) {
            throw new RuntimeException("Izba nie je dostupná v danom termíne");
        }

        long nights = ChronoUnit.DAYS.between(requestDto.getFromDate(), requestDto.getToDate());
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking booking = Booking.builder()
                .room(room)
                .user(user)
                .guestName(requestDto.getGuestName())
                .fromDate(requestDto.getFromDate())
                .toDate(requestDto.getToDate())
                .status(BookingStatus.CONFIRMED)
                .totalPrice(totalPrice)
                .note(requestDto.getNote())
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponseDto(savedBooking);
    }

    @Transactional
    public BookingResponseDto cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervácia s daným id sa nenašla"));
        booking.setStatus(BookingStatus.CANCELLED);
        return mapToResponseDto(bookingRepository.save(booking));
    }

    @Transactional
    public BookingResponseDto updateBooking(Long id, BookingRequestDto requestDto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezervácia s daným id sa nenašla"));
        booking.setFromDate(requestDto.getFromDate());
        booking.setToDate(requestDto.getToDate());
        booking.setGuestName(requestDto.getGuestName());
        booking.setNote(requestDto.getNote());
        return mapToResponseDto(bookingRepository.save(booking));
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
                .hotelName(booking.getRoom().getHotel().getName())
                .build();
    }
}