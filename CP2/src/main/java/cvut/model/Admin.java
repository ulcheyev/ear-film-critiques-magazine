package cvut.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Admin extends AppUser {

    @OneToMany(mappedBy = "admin")
    @ToString.Exclude
    @JsonBackReference
    private List<Critique> critiqueList;

    public Admin(String firstname, String lastname, String username, String password, String email) {
        super(firstname, lastname, username, password, email);
    }

}
