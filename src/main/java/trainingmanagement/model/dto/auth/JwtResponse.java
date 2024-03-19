package trainingmanagement.model.dto.auth;

import lombok.*;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String accessToken;
    private final String type = "Bearer";
    private String fullName;
    private String username;
    private Boolean status;
    private Set<String> roles;
}
