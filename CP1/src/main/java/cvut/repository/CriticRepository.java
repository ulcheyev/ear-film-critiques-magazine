package cvut.repository;
import cvut.model.Critic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriticRepository extends JpaRepository<Critic, Long> {
    List<Critic> findAllByCriticRating(double rating);
    List<Critic> findByOrderByCriticRatingDesc();
    List<Critic> findByOrderByCriticRatingAsc();
}
