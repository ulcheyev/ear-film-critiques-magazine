package cvut.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
@ToString
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

    public RatingVote(Critique critique, double stars, Date date, AppUser voteOwner) {
        this.critique = critique;
        this.stars = stars;
        this.date = date;
        this.voteOwner = voteOwner;
    }

}
