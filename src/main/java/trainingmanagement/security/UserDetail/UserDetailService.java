package trainingmanagement.security.UserDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import trainingmanagement.model.entity.User;
import trainingmanagement.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserPrincipal userPrincipal = UserPrincipal.builder().
                    user(user)
                    .authorities(user.getRoles()
                            .stream()
                            .map(item -> new SimpleGrantedAuthority(item.getRoleName().name()))
                            .collect(Collectors.toSet()))
                    .build();
            return userPrincipal;
        }
        throw new  RuntimeException("role not found");
    }
}
