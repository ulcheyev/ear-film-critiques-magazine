package cvut.model.dto;

import cvut.model.AppUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public
class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String username;
    private String role;

    public boolean fieldsIsNotEmpty(){
        return (firstname != null &&
                lastname != null &&
                email != null &&
                password != null &&
                username != null &&
                role != null);
    }


}
