package cvut.repository;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import java.util.Date;
import java.util.List;

@Repository
public interface CritiqueRepository extends JpaRepository<Critique, Long> {
    @Query()
    List<Critique> findAllByCritiqueState(CritiqueState critiqueState);
    List<Critique> findAllByDateOfAcceptance(Date date);
    List<Critique> findAllByDateOfAcceptanceAfter(Date date);
    List<Critique> findAllDateOfAcceptanceBefore(Date date);
    List<Critique> findAllDateOfAcceptanceBetween(Date date1, Date date2);
    List<Critique> findAllAdmin(User admin);
    List<Critique> findAllCritiqueOwner(User owner);

    /*TODO*/
    List<Critique>  findAllCritiqueRating(double id);
    /*TODO*/
}
