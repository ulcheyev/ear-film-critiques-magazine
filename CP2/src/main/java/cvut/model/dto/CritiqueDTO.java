package cvut.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CritiqueDTO {

    private String title;
    private String text;
    private Long filmId;

    public boolean fieldsIsNotEmpty(){
        return (title != null &&
                text != null &&
                filmId != null);
    }
}
