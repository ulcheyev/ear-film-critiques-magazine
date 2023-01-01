package cvut.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin")
@DiscriminatorValue(Admin.DISCRIMINATOR_VALUE)
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedQueries({
        @NamedQuery(name = "Admin.findAdminWithSpecifiedCritiqueQuantity", query = "SELECT admin FROM Admin admin join Critique critique on critique.admin.id = admin.id where size(admin.critiqueList)>?1 and critique.critiqueState = 'IN_PROCESSED'")
})
public class Admin extends AppUser {

    public static final String DISCRIMINATOR_VALUE = "ROLE_ADMIN";

    @OneToMany(mappedBy = "admin")
    @ToString.Exclude
    @JsonManagedReference
    private List<Critique> critiqueList;

    @OneToMany(mappedBy = "admin")
    @JsonManagedReference
    private List<Remarks> remarks;

    public Admin(String firstname, String lastname, String username, String password, String email) {
        super(firstname, lastname, username, password, email);
    }

}
