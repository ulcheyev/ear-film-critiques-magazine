package cvut.model;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class MainRole{

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

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
