package sk.umb.hotelmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingRequestDto {

    private Long roomId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String guestName;
    private String note;
}
