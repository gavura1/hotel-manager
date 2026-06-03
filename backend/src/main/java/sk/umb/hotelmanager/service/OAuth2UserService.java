package sk.umb.hotelmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import sk.umb.hotelmanager.entity.User;
import sk.umb.hotelmanager.enums.Role;
import sk.umb.hotelmanager.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request){
        OAuth2User oAuth2User = super.loadUser(request);

        String googleId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        userRepository.findByGoogleId(googleId).orElseGet(() -> {
            User user = new User();
            user.setGoogleId(googleId);
            user.setEmail(email);
            user.setName(name);
            user.setRole(Role.USER);
            user.setPassword(UUID.randomUUID().toString());
            return userRepository.save(user);
        });
        return oAuth2User;
    }
}
