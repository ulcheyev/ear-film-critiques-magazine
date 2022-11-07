package cvut.repository;

import cvut.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("select min(r.stars) from Rating r")
    double findTheLowestRating();

    @Query("select max(r.stars) from Rating r")
    double findTheHighestRating();

    @Query("select count(r) from Rating r where r.critique.id = ?1")
    int findQuantityOfVotesByCritiqueId(Long Id);

    @Query("select sum(r.stars) from Rating r where r.critique.id = ?1")
    double findSumOfVotesByCritiqueId(Long Id);


    List<Rating> findByCritique_Id(Long id);
    List<Rating> findAllByDate(Date date);
    List<Rating> findAllByDateAfter(Date date);
    List<Rating> findAllByDateBefore(Date date);
    List<Rating> findAllByDateBetween(Date date1, Date date2);
}
