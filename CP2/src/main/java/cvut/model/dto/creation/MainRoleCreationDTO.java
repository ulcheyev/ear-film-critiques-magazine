package cvut.model.dto.creation;

import cvut.model.dto.FilmDTO;
import lombok.Data;

import java.util.List;

@Data
public class MainRoleCreationDTO {

    private String lastname;
    private String firstname;
    private String filmRole;
    private List<FilmDTO> films;

}
