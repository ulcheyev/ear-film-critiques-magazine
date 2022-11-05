package cvut.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
public class Admin extends User{

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
