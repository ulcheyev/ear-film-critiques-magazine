package cvut.services;

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

    @Autowired
    private CritiqueRepository critiqueRepository;

    @Autowired
    private RatingRepository ratingRepository;

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
        ratingRepository.save(rating);
        Critic critic = critique.getCritiqueOwner();

        int critiqueCount = ratingRepository.findQuantityOfVotesByCritiqueId(critique.getId());
        double critiqueSum = ratingRepository.findSumOfVotesByCritiqueId(critique.getId());

        int criticCount = critiqueRepository.findQuantityOfCritiquesByCriticId(critic.getId());
        double criticSum = critiqueRepository.findSumOfCritiquesByCriticId(critic.getId());

        double resToCritique = critiqueSum/critiqueCount;
        double resToCritic = criticSum/criticCount;

        critique.setCritiqueRating(resToCritique);
        critic.setCriticRating(resToCritic);

        critiqueRepository.save(critique);
    }

    @Transactional
    public List<Rating> findByCritiqueId(Long id){
        return ratingRepository.findByCritique_Id(id).stream().toList();
    }

    @Transactional
    public Rating findById(Long id){
        return ratingRepository.findById(id).get();
    }

    @Transactional
    public int findQuantityOfVotesByCritiqueId(Long id){
        return ratingRepository.findQuantityOfVotesByCritiqueId(id);
    }

    @Transactional
    public double findSumOfVotesByCritiqueId(Long id){
        return ratingRepository.findSumOfVotesByCritiqueId(id);
    }


}
