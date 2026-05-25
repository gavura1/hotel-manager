package sk.umb.hotelmanager.service;

import jdk.jshell.Snippet;
import lombok.*;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import sk.umb.hotelmanager.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingService {

    private Long id;
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String guestName;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private LocalDate createdAt;
    private String note;

}
