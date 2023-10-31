package cvut.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AppUserInfoUpdateDTO {
    private String username;
    private String email;
}
