package cvut.services;

import cvut.config.utils.EarUtils;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.RatingVote;
import cvut.repository.CriticRepository;
import cvut.repository.CritiqueRepository;
import cvut.repository.RatingVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class RatingService {

    private final CritiqueRepository critiqueRepository;
    private final RatingVoteRepository ratingVoteRepository;
    private final CriticRepository criticRepository;


    @Autowired
    public RatingService(CritiqueRepository critiqueRepository, RatingVoteRepository ratingVoteRepository, CriticRepository criticRepository) {
        this.critiqueRepository = critiqueRepository;
        this.ratingVoteRepository = ratingVoteRepository;
        this.criticRepository = criticRepository;
    }

    /**
     * Creates new rating entity from the specified stars,
     * updates critique rating and critic rating
     *
     * @param stars stars from vote
     * @param critique critique
     */

    @Transactional
    public void createAndUpdate(@NonNull Critique critique, @NonNull double stars){

        RatingVote ratingVote = new RatingVote(critique, stars, new Date());
        Critic critic = critique.getCritiqueOwner();
        ratingVoteRepository.save(ratingVote);

        //Update critique
        int critiqueCount = ratingVoteRepository.findQuantityOfVotesByCritiqueId(critique.getId());
        double critiqueRatingSum = ratingVoteRepository.findSumOfVotesByCritiqueId(critique.getId());
        double critique_c = critiqueRatingSum/critiqueCount;
        double resultToCritique = EarUtils.floorNumber(1,critique_c);
        critique.setCritiqueRating(resultToCritique);
        critiqueRepository.save(critique);

        //Update critic
        int criticCount = critiqueRepository.findQuantityOfCritiquesByCriticId(critic.getId());
        double criticRatingSum = critiqueRepository.findSumOfCritiquesRatingByCriticId(critic.getId());
        double critic_c = criticRatingSum/criticCount;
        double resultToCritic = EarUtils.floorNumber(1,critic_c);
        critic.setCriticRating(resultToCritic);
        criticRepository.save(critic);

    }

    @Transactional
    public RatingVote findById(@NonNull Long id){
        return ratingVoteRepository.findById(id).get();
    }

    @Transactional
    public List<RatingVote> findByCritiqueId(@NonNull Long id){
        return ratingVoteRepository.findByCritique_Id(id).stream().toList();
    }

    @Transactional
    public int findQuantityOfVotesByCritiqueId(@NonNull Long id){
        return ratingVoteRepository.findQuantityOfVotesByCritiqueId(id);
    }

    @Transactional
    public double findSumOfVotesByCritiqueId(@NonNull Long id){
        return ratingVoteRepository.findSumOfVotesByCritiqueId(id);
    }




}
