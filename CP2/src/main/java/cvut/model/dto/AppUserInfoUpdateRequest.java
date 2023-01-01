package cvut.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AppUserInfoUpdateRequest {
    private String username;
    private String email;
}
