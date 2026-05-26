package sk.umb.hotelmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import sk.umb.hotelmanager.dto.AuthRequestDto;
import sk.umb.hotelmanager.dto.AuthResponseDto;
import sk.umb.hotelmanager.security.JwtService;
import sk.umb.hotelmanager.service.AuthService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        return authService.login(request);
    }
}
