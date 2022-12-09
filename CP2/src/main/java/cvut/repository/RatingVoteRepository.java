package cvut.repository;

import cvut.model.Film;
import cvut.model.RatingVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingVoteRepository extends JpaRepository<RatingVote, Long> {

    Optional<Double> findTheLowestRating();

    Optional<Double> findTheHighestRating();

    Optional<Integer> findQuantityOfVotesByCritiqueId(Long id);

    Optional<RatingVote> findByVoteOwner_IdAndCritique_Id(Long id1, Long id2);

    Optional<Double> findSumOfVotesByCritiqueId(Long Id);

    List<RatingVote> findByCritique_Id(Long id);

    List<RatingVote> findAllByDate(Date date);

}
