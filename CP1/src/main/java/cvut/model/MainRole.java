package cvut.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class MainRole extends AbstractEntity{

    @Enumerated(EnumType.STRING)
    private FilmRole filmRole;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @ManyToMany(mappedBy = "mainRoleList")
    private List<Film> filmList;

    public MainRole() {}

    public FilmRole getFilmRole() {
        return filmRole;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    @Override
    public String toString() {
        return "MainRole{" +
                "filmRole=" + filmRole +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", filmList=" + filmList +
                '}';
    }
}
