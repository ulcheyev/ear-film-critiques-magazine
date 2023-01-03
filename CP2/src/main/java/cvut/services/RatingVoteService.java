package cvut.services;

import cvut.model.Critique;
import cvut.model.RatingVote;

import java.util.List;

public interface RatingVoteService {
    RatingVote findById(Long id);
    RatingVote findVoteByVoteOwnerIdAndCritiqueId(Long appUserId, Long critiqueId);
    double findSumOfVotesByCritiqueId(Long id);
    List<RatingVote> findByCritiqueId(Long id);
    int findQuantityOfVotesByCritiqueId(Long id);
    void makeVoteAndUpdateCritiqueAndCriticRatings(String username, Long critiqueId, double stars);
    void deleteAndUpdate(String username,  Long critiqueId);
    void updateCritiqueAndCriticEntities(Critique critique);
}
