package cvut.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "critic")
@DiscriminatorValue(Critic.DISCRIMINATOR_VALUE)
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Critic extends AppUser {

    public static final String DISCRIMINATOR_VALUE = "ROLE_CRITIC";

    @Column(name = "critic_rating", nullable = false)
    private double criticRating;

    @OneToMany(mappedBy = "critiqueOwner", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JsonManagedReference(value = "critic-critique")
    @ToString.Exclude
    @OrderBy("dateOfAcceptance DESC")
    private List<Critique> critiqueList;

    public Critic(String firstname, String lastname, String username, String password, String email) {
        super(firstname, lastname, username, password, email);
    }

}
