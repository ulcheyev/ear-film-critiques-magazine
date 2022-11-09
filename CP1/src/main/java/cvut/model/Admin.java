package cvut.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "admin")
public class Admin extends AppUser {

    @OneToMany(mappedBy = "admin")
    private List<Critique> critiqueList;

    public Admin() {}

    public List<Critique> getCritiqueList() {
        return critiqueList;
    }

    public void setCritiqueList(List<Critique> critiqueList) {
        this.critiqueList = critiqueList;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "critiqueList=" + critiqueList +
                '}';
    }
}
