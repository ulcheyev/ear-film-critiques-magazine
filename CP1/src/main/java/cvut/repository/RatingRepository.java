package cvut.repository;

import cvut.model.Rating;
import cvut.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByCritique_Id(Long id);
    //TODO
    //find all by stars List<Vote>
}
