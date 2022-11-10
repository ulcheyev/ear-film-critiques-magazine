package cvut.model;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "rating")
public class RatingVote {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    //TODO ubrat cascade type-postavit MERGE
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "critique", referencedColumnName = "id", nullable = false)
    private Critique critique;

    @Column(name = "stars", nullable = false)
    private double stars;

    //TODO ubrat cascade type
    @ManyToOne
    @JoinColumn(name = "vote_owner", referencedColumnName = "id", nullable = false)
    private AppUser voteOwner;

    @Basic
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public RatingVote() {}

    public RatingVote(Critique critique, double stars, Date date, AppUser voteOwner) {
        this.critique = critique;
        this.stars = stars;
        this.date = date;
        this.voteOwner = voteOwner;
    }

    public Critique getCritique() {
        return critique;
    }

    public void setCritique(Critique critique) {
        this.critique = critique;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AppUser getVoteOwner() {return voteOwner;}

    public void setVoteOwner(AppUser voteOwner) {this.voteOwner = voteOwner;}

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", critique=" + critique +
                ", stars=" + stars +
                ", date=" + date +
                '}';
    }
}
