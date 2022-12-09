package cvut.repository;

import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;

@Getter
@Setter
public class CritiqueSearchCriteria {
    private Double rating;
    private String title;
    private String film;
    private String username;


    public boolean requestIsEmpty(){
        return rating == null
                &&
                title == null
                &&
                film == null
                &&
                username == null;
    }
}
