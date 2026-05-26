package sk.umb.hotelmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sk.umb.hotelmanager.dto.AuthRequestDto;
import sk.umb.hotelmanager.dto.AuthResponseDto;
import sk.umb.hotelmanager.dto.UserMeResponseDto;
import sk.umb.hotelmanager.dto.UserMeResponseDto;
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

    @GetMapping("/me")
    public UserMeResponseDto me(Authentication authentication) {
        return authService.getCurrentUser(authentication);
    }
}
