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
        @NamedQuery(name = "Critique.findByFilmIdAndRating", query = "select critique from Critique critique join Film film on critique.film.id = film.id where critique.critiqueState = 'IN_PROCESSED' and critique.rating > ?1 and film.id= ?1"),
        @NamedQuery(name = "Critique.findQuantityOfCritiquesByCriticId", query = "SELECT COUNT(c) FROM Critique c WHERE c.critiqueOwner.id = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findSumOfCritiquesRatingByCriticId", query = "SELECT SUM(c.rating) FROM Critique c WHERE c.critiqueOwner.id = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findAllByCritiqueState", query = "SELECT c FROM Critique c WHERE c.critiqueState = ?1"),
        @NamedQuery(name = "Critique.findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike", query = "SELECT c FROM Critique c WHERE c.critiqueOwner.firstname LIKE :firstname AND c.critiqueOwner.lastname LIKE :lastname AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findAllByFilm_NameLike", query = "SELECT c FROM Critique c WHERE c.film.name LIKE :name AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findAllByRating", query = "SELECT c FROM Critique c WHERE c.rating = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "Critique.findAllByCritiqueOwnerUsername", query = "SELECT c FROM Critique c WHERE c.critiqueOwner.username = ?1"),
        @NamedQuery(name = "Critique.findAllByDateOfAcceptance", query = "SELECT c FROM Critique c WHERE c.dateOfAcceptance = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
})
public class Critique {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private CritiqueState critiqueState = CritiqueState.IN_PROCESSED;

    @OneToMany(mappedBy = "critique", fetch = FetchType.EAGER)
    @JsonBackReference
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
    @JsonBackReference
    private List<Remarks> critiqueRemarks;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference("critiqueList")
    @JoinColumn(name = "admin", referencedColumnName = "id")
    private Admin  admin;

    //TODO ubrat cascade type
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "critique_owner", referencedColumnName = "id", nullable = false)
    private Critic critiqueOwner;

    @OneToMany(mappedBy = "admin")
    @JsonBackReference
    private List<Remarks> remarksList;

    @OneToMany(mappedBy = "critique")
    @JsonBackReference
    private List<Comment> comments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "film", referencedColumnName = "id", nullable = false)
    private Film film;

    public Critique(String title, String text, Film film, Critic critiqueOwner){
        this.title = title;
        this.text = text;
        this.film = film;
        this.critiqueOwner = critiqueOwner;
    }

}

