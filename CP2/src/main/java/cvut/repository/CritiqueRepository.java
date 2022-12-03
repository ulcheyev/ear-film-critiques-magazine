package cvut.repository;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CritiqueRepository extends JpaRepository<Critique, Long> {

    @Query("SELECT COUNT(c) FROM Critique c WHERE c.critiqueOwner.id = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
    Optional<Integer> findQuantityOfCritiquesByCriticId(Long id);

    @Query("SELECT SUM(c.rating) FROM Critique c WHERE c.critiqueOwner.id = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
    Optional<Double> findSumOfCritiquesRatingByCriticId(Long id);

    List<Critique> findCritiquesByCritiqueOwner_Id(Long id);

    List<Critique> findCritiquesByAdmin_Id(Long id);

    @Query("SELECT c FROM Critique c WHERE c.critiqueState = ?1")
    List<Critique> findAllByCritiqueState(CritiqueState state);

    @Query("SELECT c FROM Critique c WHERE c.critiqueOwner.firstname LIKE %:firstname% AND c.critiqueOwner.lastname LIKE %:lastname% AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
    List<Critique> findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike(String lastname, String firstname);

    @Query("SELECT c FROM Critique c WHERE c.film.name LIKE %:name% AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
    List<Critique> findAllByFilm_NameLike(String name);

    @Query("SELECT c FROM Critique c WHERE c.rating = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
    List<Critique> findAllByRating(double rating);

    @Query("SELECT c FROM Critique c WHERE c.dateOfAcceptance = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
    List<Critique> findAllByDateOfAcceptance(Date dateOfAcceptance);

    List<Critique> findByOrderByDateOfAcceptanceAsc();

    List<Critique> findByOrderByDateOfAcceptanceDesc();

}
