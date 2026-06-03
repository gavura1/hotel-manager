package sk.umb.hotelmanager.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.repository.UserRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String googleId = oAuth2User.getName();

        User user = userRepository.findByGoogleId(googleId).orElseThrow(() -> new RuntimeException("User nenájdený po OAuth2 logine"));

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        response.sendRedirect("http://localhost:4200/auth/callback?token=" + token);
    }
}
