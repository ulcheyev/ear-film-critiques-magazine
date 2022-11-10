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
    Double findTheLowestRating();

    @Query("select max(r.stars) from RatingVote r")
    Double findTheHighestRating();

    @Query("select count(r) from RatingVote r where r.critique.id = ?1")
    Integer findQuantityOfVotesByCritiqueId(Long id);

    RatingVote findByVoteOwner_IdAndCritique_Id(Long id1, Long id2);

    @Query("select sum(r.stars) from RatingVote r where r.critique.id = ?1")
    Double findSumOfVotesByCritiqueId(Long Id);

    List<RatingVote> findByCritique_Id(Long id);
    List<RatingVote> findAllByDate(Date date);
    List<RatingVote> findAllByDateAfter(Date date);
    List<RatingVote> findAllByDateBefore(Date date);
    List<RatingVote> findAllByDateBetween(Date date1, Date date2);
}
