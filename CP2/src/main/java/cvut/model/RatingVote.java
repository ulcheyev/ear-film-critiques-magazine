package cvut.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@NamedQueries({
        @NamedQuery(name = "RatingVote.findTheLowestRating", query = "SELECT MIN(r.stars) FROM RatingVote r"),
        @NamedQuery(name = "RatingVote.findTheHighestRating", query = "SELECT MAX(r.stars) FROM RatingVote r"),
        @NamedQuery(name = "RatingVote.findQuantityOfVotesByCritiqueId", query = "SELECT COUNT(r) FROM RatingVote r WHERE r.critique.id = ?1"),
        @NamedQuery(name = "RatingVote.findSumOfVotesByCritiqueId", query = "SELECT SUM(r.stars) FROM RatingVote r WHERE r.critique.id = ?1"),
})
public class RatingVote {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    //TODO ubrat cascade type-postavit MERGE
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "critique", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "rating-critique")
    @OrderBy("rating DESC")
    private Critique critique;

    @Column(name = "stars", nullable = false)
    private double stars;

    //TODO ubrat cascade type
    @ManyToOne
    @JsonBackReference("ratingVotes")
    @JoinColumn(name = "vote_owner", referencedColumnName = "id", nullable = false)
    @OrderBy("id ASC")
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
