package sk.umb.hotelmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private String role;
    private List<Long> hotelId;
}
