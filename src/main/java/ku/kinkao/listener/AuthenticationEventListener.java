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
        User user = (User) authentication.getPrincipal();

        logger.info("{} has successfully logged in with roles {} at {}",
                user.getUsername(), user.getAuthorities(), Instant.now());
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

