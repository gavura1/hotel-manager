package sk.umb.hotelmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sk.umb.hotelmanager.enums.RoomStatus;
import sk.umb.hotelmanager.enums.RoomType;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RoomResponseDto {
    private Long id;

    private String roomNumber;

    private RoomType roomType;

    private RoomStatus roomStatus;

    private BigDecimal pricePerNight;

    private Integer capacity;

    private Long hotelId;
}
