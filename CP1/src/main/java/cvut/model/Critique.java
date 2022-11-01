package cvut.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Critique extends AbstractEntity{

    @Enumerated(EnumType.STRING)
    private CritiqueState critiqueState;

    @Column(name = "critique_rating", nullable = false)
    private double critiqueRating;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "critique_text", nullable = false)
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

    public double getCritiqueRating() {
        return critiqueRating;
    }

    public void setCritiqueRating(double critiqueRating) {
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

