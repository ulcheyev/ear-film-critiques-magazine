package cvut.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "film")
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Film.findMovieNamesForASpecificPeriod", query = "select film from Film as film join Critique as critique on critique.film.id = film.id where (critique.dateOfAcceptance between ?1 and ?2) and critique.critiqueState = 'ACCEPTED'"),
        @NamedQuery(name = "Film.findMovieNamesCriticizedByAParticularCritic", query = "select film from Film as film join Critique c on film.id = c.film.id where c.critiqueOwner.id = ?1")
})

@SqlResultSetMapping(
        name = "FilmMapping",
        classes = @ConstructorResult(
                targetClass = Critique.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class)
                }
        )
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findMovieNamesForASpecificPeriodNNQ",
                query = "SELECT f.id FROM film f INNER JOIN critique c ON c.film = f.id WHERE c.date_of_acceptance BETWEEN ?1 AND ?2 AND c.critique_state = 'ACCEPTED'",
                resultSetMapping = "FilmMapping"),
        @NamedNativeQuery(
                name = "findMovieNamesCriticizedByAParticularCriticNNQ",
                query = "SELECT f.id FROM film f INNER JOIN critique c ON f.id = c.film WHERE c.critique_owner = ?1",
                resultSetMapping = "FilmMapping"
        )
})
public class Film {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "film_and_main_roles",
            joinColumns =
                    @JoinColumn(name = "film_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "main_role_id")
    )
    @JsonManagedReference
    private List<MainRole> mainRoleList;

    @OneToMany(mappedBy = "film")
    @JsonBackReference
    private List<Critique> critiques;

    @Column(name = "film_name", nullable = false)
    private String name;

    @Basic
    @Column(name = "date_of_release", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfRelease;

    @Column(name="film_description", columnDefinition = "TEXT", nullable = false)
    private String description;


}