package cvut.repository;
import cvut.model.Comment;
import cvut.model.Critic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriticRepository extends JpaRepository<Critic, Long> {

    Optional<List<Critic>> findAllByCriticRating(double rating);

    Optional<Critic> findByUsername(String username);

    Optional<Critic> findByEmail(String email);

    Optional<List<Critic>> findAllByLastnameAndFirstnameLike(String lastname, String firstname);

    Optional<List<Critic>> findByOrderByCriticRatingDesc();

    Optional<List<Critic>> findByOrderByCriticRatingAsc();
}
