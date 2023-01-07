package cvut.model.dto;

import cvut.model.CritiqueState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CritiqueCreationDTO {

    private String title;
    private String text;
    private Long filmId;

    public boolean fieldsAreNotEmpty(){
        return (title != null &&
                text != null &&
                filmId != null);
    }
}
