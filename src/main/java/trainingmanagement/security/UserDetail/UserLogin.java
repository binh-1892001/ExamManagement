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
public class UserLogin {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserLogin.class);

    public User userLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
            Optional<User> userOption = userService.getById(userPrinciple.getUser().getId());
            if (userOption.isPresent()) {
                return userOption.get();
            }
        }
        logger.error("User - UserController - User id is not found.");
        return null;
    }
}
