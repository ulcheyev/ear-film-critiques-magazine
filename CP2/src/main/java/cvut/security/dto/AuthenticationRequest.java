package cvut.security.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String username;
    private String password;

    public boolean fieldsAreNotEmpty(){
        return (password != null &&
                username != null);
    }
}
