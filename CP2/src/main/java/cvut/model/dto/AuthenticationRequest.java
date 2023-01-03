package cvut.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthenticationRequest {

    private String username;
    private String password;

    public boolean fieldsAreNotEmpty(){
        return (password != null &&
                username != null);
    }
}
