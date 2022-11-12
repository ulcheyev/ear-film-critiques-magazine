package cvut.repository;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CritiqueRepository extends JpaRepository<Critique, Long> {

    @Query("SELECT COUNT(c) FROM Critique c WHERE c.critiqueOwner.id = ?1")
    Optional<Integer> findQuantityOfCritiquesByCriticId(Long id);

    @Query("SELECT SUM(c.rating) FROM Critique c WHERE c.critiqueOwner.id = ?1")
    Optional<Double> findSumOfCritiquesRatingByCriticId(Long id);

    Optional<List<Critique>> findCritiquesByCritiqueOwner_Id(Long id);

    Optional<List<Critique>> findCritiqueByCritiqueState(CritiqueState critiqueState);

    Optional<List<Critique>> findCritiquesByAdmin_Id(Long id);

    Optional<List<Critique>> findAllByCritiqueState(CritiqueState critiqueState);

    Optional<List<Critique>> findAllByDateOfAcceptance(Date dateOfAcceptance);


}
