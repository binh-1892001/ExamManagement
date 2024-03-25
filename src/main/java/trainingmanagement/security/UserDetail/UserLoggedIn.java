package trainingmanagement.security.UserDetail;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import trainingmanagement.model.entity.User;
import trainingmanagement.service.UserService;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoggedIn {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserLoggedIn.class);

    public User getUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
            Optional<User> userOption = userService.getUserById(userPrinciple.getUser().getId());
            if (userOption.isPresent()) return userOption.get();
        }
        logger.error("User - UserController - User id is not found.");
        return null;
    }
}
