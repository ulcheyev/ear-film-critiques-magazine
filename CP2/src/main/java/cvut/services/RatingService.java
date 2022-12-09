package cvut.services;

import cvut.model.Critique;
import cvut.model.RatingVote;
import org.springframework.lang.NonNull;

import java.util.List;

public interface RatingService {
    RatingVote findById(Long id);
    RatingVote findVoteByVoteOwnerIdAndCritiqueId(Long appUserId, Long critiqueId);
    double findSumOfVotesByCritiqueId(Long id);
    List<RatingVote> findByCritiqueId(Long id);
    int findQuantityOfVotesByCritiqueId(Long id);
    void makeVoteAndUpdateCritiqueAndCriticRatings(Long appUserId, Long critiqueId,double stars);
    void deleteAndUpdate(Long appUserId,  Long critiqueId);
    void updateCritiqueAndCriticEntities(Critique critique);
}
