package cvut.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "critique")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedQueries({
        @NamedQuery(name = "Critique.findByFilmIdAndRating", query = "select critique from Critique critique join Film film on critique.film.id = film.id where critique.critiqueState = cvut.model.CritiqueState.IN_PROCESSED and critique.rating > ?1 and film.id= ?2"),
        @NamedQuery(name = "Critique.findQuantityOfCritiquesByCriticId", query = "SELECT count(c) FROM Critique c WHERE c.critiqueOwner.id = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findSumOfCritiquesRatingByCriticId", query = "SELECT SUM(c.rating) FROM Critique c WHERE c.critiqueOwner.id = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findAllByCritiqueState", query = "SELECT c FROM Critique c WHERE c.critiqueState = ?1"),
        @NamedQuery(name = "Critique.findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike", query = "SELECT c FROM Critique c WHERE c.critiqueOwner.firstname LIKE ?1 AND c.critiqueOwner.lastname LIKE ?2 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findAllByFilm_NameLike", query = "SELECT c FROM Critique c WHERE c.film.name LIKE ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findAllByRating", query = "SELECT c FROM Critique c WHERE c.rating = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findAllByCritiqueOwnerUsername", query = "SELECT c FROM Critique c WHERE c.critiqueOwner.username = ?1"),
        @NamedQuery(name = "Critique.findAllByDateOfAcceptance", query = "SELECT c FROM Critique c WHERE c.dateOfAcceptance = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
})
@SqlResultSetMapping(
        name = "CritiqueMapping",
        classes = @ConstructorResult(
                targetClass = Critique.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class)
                }
        )
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findByFilmIdAndRatingNNQ",
                query = "SELECT c.id FROM critique c INNER JOIN film f ON c.film = f.id WHERE c.rating > ?1 AND f.id = ?2 AND c.critique_state = 'IN_PROCESSED'",
                resultSetMapping = "CritiqueMapping"),
        @NamedNativeQuery(
                name = "findAllByCritiqueStateNNQ",
                query = "SELECT c.id FROM critique c WHERE c.critique_state = ?1",
                resultSetMapping = "CritiqueMapping"
        ),
        @NamedNativeQuery(
                name = "findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLikeNNQ",
                query = "SELECT c.id FROM critique c INNER JOIN app_user u ON c.critique_owner= u.id "
                        + "WHERE c.critique_state = 'ACCEPTED' AND u.lastname LIKE ?1 AND u.firstname LIKE ?2",
                resultSetMapping = "CritiqueMapping"
        )

})
public class Critique {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private CritiqueState critiqueState = CritiqueState.IN_PROCESSED;

    @OneToMany(mappedBy = "critique", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "rating-critique")
    private List<RatingVote> critiqueRatingVote;

    @Column(name = "title",columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(name = "critique_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "rating")
    private double rating = 0.0;

    @Basic
    @Column(name = "date_of_acceptance")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfAcceptance;

    @OneToMany(mappedBy = "critique")
    @JsonManagedReference(value = "remarks-critique")
    @OrderBy("remarksMakeDay DESC")
    private List<Remarks> critiqueRemarks;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "admin", referencedColumnName = "id")
    @OrderBy("id ASC")
    @JsonBackReference(value = "admin-critique")
    private Admin  admin;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "critique_owner", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "critic-critique")
    @OrderBy("ratingVotes DESC")
    private Critic critiqueOwner;


    @OneToMany(mappedBy = "critique",fetch = FetchType.EAGER)
    @JsonManagedReference(value = "comment-critique")
    @OrderBy("appUser ASC")
    private List<Comment> comments;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "film", referencedColumnName = "id", nullable = false)
    @OrderBy("dateOfRelease DESC")
    @JsonBackReference(value = "film-critique")
    private Film film;

    public Critique(String title, String text, Film film, Critic critiqueOwner){
        this.title = title;
        this.text = text;
        this.film = film;
        this.critiqueOwner = critiqueOwner;
    }

    public Critique(Long id) {
        this.id = id;
    }
}

