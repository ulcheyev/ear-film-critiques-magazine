package cvut.repository;
import cvut.model.Critique;
import cvut.model.CritiqueState;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CritiqueRepository extends JpaRepository<Critique, Long> {

    Optional<Integer> findQuantityOfCritiquesByCriticId(Long id);

    Optional<Double> findSumOfCritiquesRatingByCriticId(Long id);

    List<Critique> findCritiquesByCritiqueOwner_Id(Long id);

//    Page<Critique> findAll(Pageable pageable);

    List<Critique> findCritiquesByAdmin_Id(Long id);

    List<Critique> findAllByCritiqueState(CritiqueState state);

    List<Critique> findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike(String lastname, String firstname);

    List<Critique> findAllByFilm_NameLike(String name);

    List<Critique> findAllByRating(double rating);

    List<Critique> findAllByDateOfAcceptance(Date dateOfAcceptance);

    List<Critique> findByOrderByDateOfAcceptanceAsc();

    List<Critique> findByOrderByDateOfAcceptanceDesc();

    List<Critique> findAllByCritiqueOwnerUsername(String username);

    List<Critique> findByFilmIdAndRating(double rating, Long id);

}
