package cvut.repository;
import cvut.model.Critic;
import cvut.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//iz ratinga z kritiky
@Repository
public interface CriticRepository extends UserRepository {

    /*TODO*/

    @Query("select c from Critic c where c.criticRating > ?1")
    List<Critic> findAllByRatingAfter(double rating);

    @Query("select c from Critic c where c.criticRating < ?1")
    List<Critic> findAllByRatingBefore(double rating);

    @Query("select c from Critic c where c.criticRating > ?1 and c.criticRating < ?2")
    List<Critic> findAllByRatingBetween(double rating1, double rating2);

    @Query("select c from Critic c where c.criticRating > ?1")
    Critic findWithLowestRating();

    @Query("select c from Critic c where c.criticRating > ?1")
    Critic findWithHighestRating();

    /*TODO*/
}
