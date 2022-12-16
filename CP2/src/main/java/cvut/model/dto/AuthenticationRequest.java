package cvut.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;

    public boolean fieldsIsNotEmpty(){
        return (password != null &&
                username != null);
    }
}
