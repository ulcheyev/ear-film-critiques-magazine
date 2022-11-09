package cvut.repository;

import cvut.model.RatingVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RatingVoteRepository extends JpaRepository<RatingVote, Long> {

    @Query("select min(r.stars) from RatingVote r")
    double findTheLowestRating();

    @Query("select max(r.stars) from RatingVote r")
    double findTheHighestRating();

    @Query("select count(r) from RatingVote r where r.critique.id = ?1")
    int findQuantityOfVotesByCritiqueId(Long Id);

    @Query("select sum(r.stars) from RatingVote r where r.critique.id = ?1")
    double findSumOfVotesByCritiqueId(Long Id);

    List<RatingVote> findByCritique_Id(Long id);
    List<RatingVote> findAllByDate(Date date);
    List<RatingVote> findAllByDateAfter(Date date);
    List<RatingVote> findAllByDateBefore(Date date);
    List<RatingVote> findAllByDateBetween(Date date1, Date date2);
}
