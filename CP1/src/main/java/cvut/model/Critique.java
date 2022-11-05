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
    private CritiqueState critiqueState;

    @OneToOne
    @JoinColumn(name = "critique_rating")
    private Rating critiqueRating;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "critique_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Basic
    @Column(name = "date_of_acceptance")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfAcceptance;

    @ManyToOne
    @JoinColumn(name = "acceptance_admin", referencedColumnName = "id")
    private User admin;

    @ManyToOne
    @JoinColumn(name = "critique_owner", referencedColumnName = "id", nullable = false)
    private User critiqueOwner;

    @OneToMany(mappedBy = "admin")
    private List<Remarks> remarksList;

    public Critique() {}

    public CritiqueState getCritiqueState() {
        return critiqueState;
    }

    public void setCritiqueState(CritiqueState critiqueState) {
        this.critiqueState = critiqueState;
    }

    public Rating getCritiqueRating() {
        return critiqueRating;
    }

    public void setCritiqueRating(Rating critiqueRating) {
        this.critiqueRating = critiqueRating;
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

    public User getCritiqueOwner() {
        return critiqueOwner;
    }

    public void setCritiqueOwner(User critiqueOwner) {
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

