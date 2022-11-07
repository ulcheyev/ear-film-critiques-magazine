package cvut.model;
import javax.persistence.*;
import java.util.List;

@Entity
public class Critic extends User{

    @Column(name = "critic_rating", nullable = false)
    private double criticRating;

    @OneToMany(mappedBy = "critiqueOwner")
    private List<Critique> critiqueList;

    public Critic() {}

    public Critic(String firstname, String lastname, String username, String password) {
        super(firstname, lastname, username, password);
    }

    public double getCriticRating() {
        return criticRating;
    }

    public void setCriticRating(double criticRating) {
        this.criticRating = criticRating;
    }

    public List<Critique> getCritiqueList() {
        return critiqueList;
    }

    public void setCritiqueList(List<Critique> critiqueList) {
        this.critiqueList = critiqueList;
    }

    @Override
    public String toString() {
        return "Critic{" +
                "criticRating=" + criticRating +
                ", critiqueList=" + critiqueList +
                '}';
    }
}
