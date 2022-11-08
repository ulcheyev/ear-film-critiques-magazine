package cvut.services;

import cvut.config.utils.EarUtils;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.Rating;
import cvut.repository.CriticRepository;
import cvut.repository.CritiqueRepository;
import cvut.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class RatingService {

    private final CritiqueRepository critiqueRepository;
    private final RatingRepository ratingRepository;
    private final CriticRepository criticRepository;


    @Autowired
    public RatingService(CritiqueRepository critiqueRepository, RatingRepository ratingRepository, CriticRepository criticRepository) {
        this.critiqueRepository = critiqueRepository;
        this.ratingRepository = ratingRepository;
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

        Rating rating = new Rating(critique, stars, new Date());
        Critic critic = critique.getCritiqueOwner();
        ratingRepository.save(rating);

        //Update critique
        int critiqueCount = ratingRepository.findQuantityOfVotesByCritiqueId(critique.getId());
        double critiqueRatingSum = ratingRepository.findSumOfVotesByCritiqueId(critique.getId());
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
    public Rating findById(@NonNull Long id){
        return ratingRepository.findById(id).get();
    }

    @Transactional
    public List<Rating> findByCritiqueId(@NonNull Long id){
        return ratingRepository.findByCritique_Id(id).stream().toList();
    }

    @Transactional
    public int findQuantityOfVotesByCritiqueId(@NonNull Long id){
        return ratingRepository.findQuantityOfVotesByCritiqueId(id);
    }

    @Transactional
    public double findSumOfVotesByCritiqueId(@NonNull Long id){
        return ratingRepository.findSumOfVotesByCritiqueId(id);
    }




}
