package sk.umb.hotelmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserHotelsRequestDto {
    private List<Long> hotelId;
}
