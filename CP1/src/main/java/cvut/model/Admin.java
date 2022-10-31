package cvut.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.OneToMany;
import java.util.List;

@DiscriminatorValue("Admin")
public class Admin extends User{

    @OneToMany
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
