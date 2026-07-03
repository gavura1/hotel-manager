package sk.umb.hotelmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import sk.umb.hotelmanager.dto.AuthRequestDto;
import sk.umb.hotelmanager.dto.AuthResponseDto;
import sk.umb.hotelmanager.dto.UserMeResponseDto;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.enums.Role;
import sk.umb.hotelmanager.repository.UserRepository;
import sk.umb.hotelmanager.security.JwtService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("admin@gmail.com")
                .name("Admin Systému")
                .role(Role.ADMIN)
                .build();
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        AuthRequestDto request = new AuthRequestDto("admin@gmail.com", "heslo");

        when(userRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken("admin@gmail.com", "ADMIN")).thenReturn("fake-jwt-token");

        AuthResponseDto response = authService.login(request);

        assertThat(response.token()).isEqualTo("fake-jwt-token");
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void login_shouldThrow401_whenPasswordIsWrong() {
        AuthRequestDto request = new AuthRequestDto("admin@gmail.com", "zleHeslo");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Nesprávne prihlasovacie údaje");
    }

    @Test
    void login_shouldThrow401_whenUserNotFoundInDatabase() {
        AuthRequestDto request = new AuthRequestDto("neexistuje@gmail.com", "heslo");

        when(userRepository.findByEmail("neexistuje@gmail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User nenájdený");
    }

    @Test
    void getCurrentUser_shouldReturnUserDto_whenUserExists() {
        when(authentication.getName()).thenReturn("admin@gmail.com");
        when(userRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(testUser));

        UserMeResponseDto result = authService.getCurrentUser(authentication);

        assertThat(result.email()).isEqualTo("admin@gmail.com");
        assertThat(result.role()).isEqualTo("ADMIN");
    }
}