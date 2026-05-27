package sk.umb.hotelmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HotelResponseDto {

    private Long id;
    private String name;
    private String address;
    private String description;
}
