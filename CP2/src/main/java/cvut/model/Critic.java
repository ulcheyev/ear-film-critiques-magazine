package cvut.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "critic")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Critic extends AppUser {

    @Column(name = "critic_rating", nullable = false)
    private double criticRating;

    @OneToMany(mappedBy = "critiqueOwner", fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private List<Critique> critiqueList;

    public Critic(String firstname, String lastname, String username, String password, String email) {
        super(firstname, lastname, username, password, email);
    }

}
