package cvut.repository;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface CritiqueRepository extends JpaRepository<Critique, Long> {

    @Query("select count(c) from Critique c where c.critiqueOwner.id = ?1")
    int findQuantityOfCritiquesByCriticId(Long id);

    @Query("select sum(c.rating) from Critique c where c.critiqueOwner.id = ?1")
    double findSumOfCritiquesByCriticId(Long id);

    List<Critique> findAllByCritiqueState(CritiqueState critiqueState);
    List<Critique> findAllByDateOfAcceptance(Date dateOfAcceptance);
    List<Critique> findAllByDateOfAcceptanceAfter(Date dateOfAcceptance);
    List<Critique> findAllByDateOfAcceptanceBefore(Date dateOfAcceptance);
    List<Critique> findAllByDateOfAcceptanceBetween(Date date1, Date date2);
    List<Critique> findAllByAdmin_Id(Long id);
    List<Critique> findAllByCritiqueOwner_Id(Long id);


//    /*TODO*/
//    List<Critique>  findAllCritiqueRating(double rating);
//    /*TODO*/
}
