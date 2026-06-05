package sk.umb.hotelmanager.dto;

import lombok.*;
import sk.umb.hotelmanager.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDto {

    private Long id;
    private Long roomId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String guestName;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private String hotelName;
    private String note;

}
