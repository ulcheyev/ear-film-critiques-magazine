package cvut.repository;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@NamedQueries({
        @NamedQuery(name = "CritiqueRepository.findByCritiqueOwnerId", query = "SELECT COUNT(c) FROM Critique c WHERE c.critiqueOwner.id = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CritiqueRepository.findQuantityOfCritiquesByCriticId", query = "SELECT critique FROM Critique critique WHERE critique.critiqueOwner.id = ?1 AND critique.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CritiqueRepository.findSumOfCritiquesRatingByCriticId", query = "SELECT SUM(c.rating) FROM Critique c WHERE c.critiqueOwner.id = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CritiqueRepository.findAllByCritiqueState", query = "SELECT c FROM Critique c WHERE c.critiqueState = ?1"),
        @NamedQuery(name = "CritiqueRepository.findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike", query = "SELECT c FROM Critique c WHERE c.critiqueOwner.firstname LIKE :firstname AND c.critiqueOwner.lastname LIKE :lastname AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CritiqueRepository.findAllByFilm_NameLike", query = "SELECT c FROM Critique c WHERE c.film.name LIKE :name AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CritiqueRepository.findAllByRating", query = "SELECT c FROM Critique c WHERE c.rating = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED"),
        @NamedQuery(name = "CritiqueRepository.findAllByDateOfAcceptance", query = "SELECT c FROM Critique c WHERE c.dateOfAcceptance = ?1 AND c.critiqueState = cvut.model.CritiqueState.ACCEPTED")
})
public interface CritiqueRepository extends JpaRepository<Critique, Long> {

    Optional<Integer> findQuantityOfCritiquesByCriticId(Long id);

    Optional<Double> findSumOfCritiquesRatingByCriticId(Long id);

    List<Critique> findCritiquesByCritiqueOwner_Id(Long id);

    List<Critique> findCritiquesByAdmin_Id(Long id);

    List<Critique> findAllByCritiqueState(CritiqueState state);

    List<Critique> findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike(String lastname, String firstname);

    List<Critique> findAllByFilm_NameLike(String name);

    List<Critique> findAllByRating(double rating);

    List<Critique> findAllByDateOfAcceptance(Date dateOfAcceptance);

    List<Critique> findByOrderByDateOfAcceptanceAsc();

    List<Critique> findByOrderByDateOfAcceptanceDesc();

}
