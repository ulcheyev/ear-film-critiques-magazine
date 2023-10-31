package cvut.model.dto.creation;

import cvut.model.dto.MainRoleDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class FilmCreationDTO {
    private String filmDescription;
    private String filmName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfRelease;
    private List<MainRoleDTO> mainRoles;

}
