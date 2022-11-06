package cvut.model;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Rating {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @OneToOne
    private Critique critique;

    @OneToMany(mappedBy = "rating")
    private List<Vote> votes;

    public Rating() {}

    public Critique getCritique() {
        return critique;
    }

    public void setCritique(Critique critique) {
        this.critique = critique;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", critique=" + critique +
                ", votes=" + votes +
                '}';
    }
}
