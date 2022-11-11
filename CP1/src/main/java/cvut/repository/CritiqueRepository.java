package cvut.repository;
import cvut.model.Critique;
import cvut.model.CritiqueState;
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
    double findSumOfCritiquesRatingByCriticId(Long id);

    //TODO select na critique state
    List<Critique> findCritiquesByCritiqueOwner_Id(Long id);
    List<Critique> findCritiquesByAdmin_Id(Long id);

    List<Critique> findAllByCritiqueState(CritiqueState critiqueState);
    List<Critique> findAllByDateOfAcceptance(Date dateOfAcceptance);


}
