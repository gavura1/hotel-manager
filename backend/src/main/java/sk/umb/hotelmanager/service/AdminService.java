package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.umb.hotelmanager.dto.UserResponseDto;
import sk.umb.hotelmanager.dto.UserRoleRequestDto;
import sk.umb.hotelmanager.dto.UserHotelsRequestDto;
import sk.umb.hotelmanager.entity.Hotel;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.enums.Role;
import sk.umb.hotelmanager.repository.HotelRepository;
import sk.umb.hotelmanager.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Transactional
    public UserResponseDto updateUserRole(Long id, UserRoleRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Používateľ nebol nájdený"));

        Role newRole = Role.valueOf(request.getRole());

        // ak meníme z MANAGER na USER, odoberieme mu hotely
        if (user.getRole() == Role.MANAGER && newRole != Role.MANAGER) {
            hotelRepository.findByManagerId(id).forEach(hotel -> {
                hotel.setManager(null);
                hotelRepository.save(hotel);
            });
        }

        user.setRole(newRole);
        return mapToDto(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto updateUserHotels(Long id, UserHotelsRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Používateľ nebol nájdený"));

        hotelRepository.findByManagerId(id).forEach(hotel -> {
            hotel.setManager(null);
            hotelRepository.save(hotel);
        });

        if (request.getHotelIds() != null) {
            request.getHotelIds().forEach(hotelId -> {
                Hotel hotel = hotelRepository.findById(hotelId)
                        .orElseThrow(() -> new RuntimeException("Hotel nebol nájdený"));
                hotel.setManager(user);
                hotelRepository.save(hotel);
            });
        }

        // načítaj usera znova aby sa prejavili zmeny
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Používateľ nebol nájdený"));
        return mapToDto(updatedUser);
    }


    private UserResponseDto mapToDto(User user) {
        List<Long> hotelIds = hotelRepository.findByManagerId(user.getId())
                .stream().map(Hotel::getId).toList();

        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .hotelIds(hotelIds)
                .build();
    }
}