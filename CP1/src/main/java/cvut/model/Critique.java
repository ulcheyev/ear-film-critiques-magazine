package cvut.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "critique")
public class Critique {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Enumerated(EnumType.STRING)
    private CritiqueState critiqueState = CritiqueState.IN_PROCESSED;

    @OneToMany(mappedBy = "critique")
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
    private List<Remarks> critiqueRemarks;

    //TODO ubrat cascade type
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "admin", referencedColumnName = "id")
    private Admin  admin;

    //TODO ubrat cascade type
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "critique_owner", referencedColumnName = "id", nullable = false)
    private Critic critiqueOwner;

    @OneToMany(mappedBy = "admin")
    private List<Remarks> remarksList;

    @OneToMany(mappedBy = "critique")
    private List<Comment> comments;

    //TODO ubrat cascade type
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "film", referencedColumnName = "id", nullable = false)
    private Film film;

    public Critique() {}

    public CritiqueState getCritiqueState() {
        return critiqueState;
    }

    public void setCritiqueState(CritiqueState critiqueState) {
        this.critiqueState = critiqueState;
    }

    public double getCritiqueRating() {
        return rating;
    }

    public void setCritiqueRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateOfAcceptance() {
        return dateOfAcceptance;
    }

    public void setDateOfAcceptance(Date dateOfAcceptance) {
        this.dateOfAcceptance = dateOfAcceptance;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Critic getCritiqueOwner() {
        return critiqueOwner;
    }

    public void setCritiqueOwner(Critic critiqueOwner) {
        this.critiqueOwner = critiqueOwner;
    }

    public List<Remarks> getRemarksList() {
        return remarksList;
    }

    public void setRemarksList(List<Remarks> remarksList) {
        this.remarksList = remarksList;
    }

    public Film getFilm() {return film;}

    public void setFilm(Film film) {this.film = film;}

    @Override
    public String toString() {
        return "Critique{" +
                "critiqueState=" + critiqueState +
                ", critiqueRating=" + critiqueRatingVote +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", dateOfAcceptance=" + dateOfAcceptance +
                ", admin=" + admin +
                ", critiqueOwner=" + critiqueOwner +
                ", remarksList=" + remarksList +
                '}';
    }
}

