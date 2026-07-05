package sk.umb.hotelmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.umb.hotelmanager.dto.BookingRequestDto;
import sk.umb.hotelmanager.dto.BookingResponseDto;
import sk.umb.hotelmanager.entity.Booking;
import sk.umb.hotelmanager.entity.Hotel;
import sk.umb.hotelmanager.entity.Room;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.enums.BookingStatus;
import sk.umb.hotelmanager.enums.RoomStatus;
import sk.umb.hotelmanager.enums.RoomType;
import sk.umb.hotelmanager.repository.BookingRepository;
import sk.umb.hotelmanager.repository.RoomRepository;
import sk.umb.hotelmanager.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    private Room testRoom;
    private User testUser;
    private Hotel testHotel;

    @BeforeEach
    void setUp() {
        testHotel = Hotel.builder().id(1L).name("Grand Hotel").build();
        testRoom = Room.builder()
                .id(10L)
                .roomNumber("101")
                .roomType(RoomType.TWO_BED)
                .roomStatus(RoomStatus.AVAILABLE)
                .pricePerNight(new BigDecimal("50.00"))
                .capacity(2)
                .hotel(testHotel)
                .build();
        testUser = User.builder().id(5L).email("user1@gmail.com").build();
    }

    @Test
    void createBooking_shouldCalculateCorrectTotalPrice_forThreeNights() {
        BookingRequestDto request = new BookingRequestDto();
        request.setRoomId(10L);
        request.setFromDate(LocalDate.of(2026, 8, 1));
        request.setToDate(LocalDate.of(2026, 8, 4));
        request.setGuestName("Ján Testovací");

        when(roomRepository.findById(10L)).thenReturn(Optional.of(testRoom));
        when(userRepository.findByEmail("user1@gmail.com")).thenReturn(Optional.of(testUser));
        when(roomRepository.findAvailableRooms(1L, request.getFromDate(), request.getToDate()))
                .thenReturn(List.of(testRoom));
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        BookingResponseDto response = bookingService.createBooking(request, "user1@gmail.com");

        assertThat(response.getTotalPrice()).isEqualByComparingTo(new BigDecimal("150.00"));
    }

    @Test
    void createBooking_shouldThrow_whenRoomNotAvailableInDateRange() {
        BookingRequestDto request = new BookingRequestDto();
        request.setRoomId(10L);
        request.setFromDate(LocalDate.of(2026, 8, 1));
        request.setToDate(LocalDate.of(2026, 8, 4));

        when(roomRepository.findById(10L)).thenReturn(Optional.of(testRoom));
        when(userRepository.findByEmail("user1@gmail.com")).thenReturn(Optional.of(testUser));
        when(roomRepository.findAvailableRooms(1L, request.getFromDate(), request.getToDate()))
                .thenReturn(List.of());

        assertThatThrownBy(() -> bookingService.createBooking(request, "user1@gmail.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("nie je dostupná");
    }

    @Test
    void createBooking_shouldThrow_whenRoomDoesNotExist() {
        BookingRequestDto request = new BookingRequestDto();
        request.setRoomId(999L);

        when(roomRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.createBooking(request, "user1@gmail.com"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("nenašla");
    }

    @Test
    void cancelBooking_shouldSetStatusToCancelled() {
        Booking booking = Booking.builder()
                .id(1L)
                .room(testRoom)
                .status(BookingStatus.CONFIRMED)
                .totalPrice(new BigDecimal("100.00"))
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now().plusDays(1))
                .build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        BookingResponseDto response = bookingService.cancelBooking(1L);

        assertThat(response.getStatus()).isEqualTo(BookingStatus.CANCELLED);
    }
}