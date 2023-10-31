package cvut.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CritiqueDTO {

    private Double rating;
    private String title;
    private String film;
    private String username;


    public boolean requestIsEmpty() {
        return rating == null
                &&
                title == null
                &&
                film == null
                &&
                username == null;
    }
}
