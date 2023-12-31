package cvut.repository;

import cvut.model.Critic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriticRepository extends JpaRepository<Critic, Long> {

    List<Critic> findAllByCriticRating(double rating);

    Optional<Critic> findByUsername(String username);

    Optional<Critic> findByEmail(String email);

    List<Critic> findAllByLastnameAndFirstnameLike(String lastname, String firstname);

    List<Critic> findByOrderByCriticRatingDesc();

    List<Critic> findByOrderByCriticRatingAsc();
}
