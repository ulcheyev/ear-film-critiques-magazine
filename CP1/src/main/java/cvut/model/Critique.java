package cvut.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Critique {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Enumerated(EnumType.STRING)
    private CritiqueState critiqueState = CritiqueState.IN_PROCESSED;

    @OneToMany(mappedBy = "critique")
    private List<Rating> critiqueRating;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "critique_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "rating")
    private double rating = 0.0;

    @Basic
    @Column(name = "date_of_acceptance")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfAcceptance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acceptance_admin", referencedColumnName = "id")
    private User admin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "critique_owner", referencedColumnName = "id", nullable = false)
    private Critic critiqueOwner;

    @OneToMany(mappedBy = "admin")
    private List<Remarks> remarksList;

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

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
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

    @Override
    public String toString() {
        return "Critique{" +
                "critiqueState=" + critiqueState +
                ", critiqueRating=" + critiqueRating +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", dateOfAcceptance=" + dateOfAcceptance +
                ", admin=" + admin +
                ", critiqueOwner=" + critiqueOwner +
                ", remarksList=" + remarksList +
                '}';
    }
}

