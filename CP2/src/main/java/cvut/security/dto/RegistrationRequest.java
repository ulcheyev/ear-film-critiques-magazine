package cvut.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String username;
    private String role;

    public boolean fieldsAreNotEmpty() {
        return (firstname != null &&
                lastname != null &&
                email != null &&
                password != null &&
                username != null &&
                role != null);
    }


}
