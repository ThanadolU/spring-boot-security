package ku.kinkao.listener;

import ku.kinkao.service.SignupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import java.time.Instant;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {

    Logger logger =
            LoggerFactory.getLogger(AuthenticationEventListener.class);

    @Autowired
    private SignupService signupService;

    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            // Handle authentication with username/password
            User user = (User) authentication.getPrincipal();
            logger.info("{} has successfully logged in with roles {} at {}",
                    user.getUsername(), user.getAuthorities(), Instant.now());
        } else if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            // Handle authentication with OAuth2 (e.g., GitHub)
            DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
            String username = oauth2User.getAttribute("login"); // Assuming GitHub login is used as username
            String roles = oauth2User.getAuthorities().toString();
            logger.info("{} has successfully logged in with roles {} at {}",
                    username, roles, Instant.now());
        } else {
            logger.warn("Unknown principal type: {}", authentication.getPrincipal().getClass().getName());
        }
    }

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {

        String username = (String) event.getAuthentication().getPrincipal();

        if (signupService.isUsernameAvailable(username))
            logger.warn("Failed login attempt [incorrect USERNAME]");
        else
            logger.warn("Failed login attempt [incorrect PASSWORD]");
    }
}

