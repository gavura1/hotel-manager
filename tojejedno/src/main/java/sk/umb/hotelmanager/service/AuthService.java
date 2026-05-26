package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import sk.umb.hotelmanager.dto.AuthRequestDto;
import sk.umb.hotelmanager.dto.AuthResponseDto;
import sk.umb.hotelmanager.dto.UserMeResponseDto;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.repository.UserRepository;
import sk.umb.hotelmanager.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponseDto login(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String token = jwtService.generateToken(request.username());

        return new AuthResponseDto(token);
    }

    public UserMeResponseDto getCurrentUser(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Používateľ nebol nájdený"));

        return new UserMeResponseDto(user.getEmail(), user.getName(), user.getRole().name());
    }
}
