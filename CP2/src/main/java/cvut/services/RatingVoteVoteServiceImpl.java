package cvut.services;

import cvut.config.utils.EarUtils;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.RatingVote;
import cvut.repository.AppUserRepository;
import cvut.repository.CriticRepository;
import cvut.repository.CritiqueRepository;
import cvut.repository.RatingVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RatingVoteVoteServiceImpl implements RatingVoteService {

    private final CritiqueRepository critiqueRepository;
    private final RatingVoteRepository ratingVoteRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public RatingVoteVoteServiceImpl(CritiqueRepository critiqueRepository, RatingVoteRepository ratingVoteRepository, AppUserRepository appUserRepository) {
        this.critiqueRepository = critiqueRepository;
        this.ratingVoteRepository = ratingVoteRepository;
        this.appUserRepository = appUserRepository;
    }


    public RatingVote findVoteByVoteOwnerIdAndCritiqueId(@NonNull Long appUserId, @NonNull Long critiqueId) {
        Optional<RatingVote> byVoteOwner_idAndCritique_id = ratingVoteRepository.findByVoteOwner_IdAndCritique_Id(appUserId, critiqueId);
        if(byVoteOwner_idAndCritique_id.isEmpty()){
            throw new NotFoundException("Vote with specified user id "+appUserId+" and critique id "+critiqueId+" does not exist");
        }
        return byVoteOwner_idAndCritique_id.get();
    }

    public RatingVote findById(@NonNull Long id) {
        Optional<RatingVote> byId = ratingVoteRepository.findById(id);
        if(byId.isEmpty()){
            throw new NotFoundException("Vote with specified  id "+id+" does not exist");
        }
        return byId.get();
    }

    public double findSumOfVotesByCritiqueId(@NonNull Long id) {
        Optional<Double> sumOfVotesByCritiqueId = ratingVoteRepository.findSumOfVotesByCritiqueId(id);
        if(sumOfVotesByCritiqueId.isEmpty()){
            return 0;
        }
        return sumOfVotesByCritiqueId.get();

    }

    public List<RatingVote> findByCritiqueId(@NonNull Long id) {
        List<RatingVote> byCritique_id = ratingVoteRepository.findByCritique_Id(id);
        if(byCritique_id.isEmpty()){
            throw new NotFoundException("Critique with id "+id+"does not exist in RatingVote table");
        }
        return byCritique_id;
    }

    public int findQuantityOfVotesByCritiqueId(@NonNull Long id) {
        Optional<Integer> quantityOfVotesByCritiqueId =
                ratingVoteRepository.findQuantityOfVotesByCritiqueId(id);

        if(quantityOfVotesByCritiqueId.isEmpty()){
            return 0;
        }
        return quantityOfVotesByCritiqueId.get();

    }



    @Transactional
    public void makeVoteAndUpdateCritiqueAndCriticRatings(@NonNull String username,
                                                          @NonNull Long critiqueId,
                                                          @NonNull double stars) {

        if (stars < 0 || stars > 5) {
            throw new ValidationException("Stars quantity must be greater or equals than 0 and less or equals than 5");
        }

        Critique critique = critiqueRepository.findById(critiqueId)
                .orElseThrow(()->new NotFoundException("Critique with id "+critiqueId+"does not found"));
        AppUser appUser = appUserRepository.findAppUserByUsername(username)
                .orElseThrow(()->new NotFoundException("AppUser with username "+username+" does not found"));

        RatingVote ratingVote;
        try{
            ratingVote = findVoteByVoteOwnerIdAndCritiqueId(appUser.getId(), critiqueId);
        }catch (NotFoundException e){
            ratingVote = new RatingVote(critique, stars, new Date(), appUser);
        }
        ratingVoteRepository.save(ratingVote);
        updateCritiqueAndCriticEntities(critique);
    }


    @Transactional
    public void deleteAndUpdate(@NonNull String username, @NonNull Long critiqueId) {
        AppUser appUser = appUserRepository.findAppUserByUsername(username)
                .orElseThrow(()->new NotFoundException("AppUser with username "+username+" does not found"));
        RatingVote ratingVote = findVoteByVoteOwnerIdAndCritiqueId(appUser.getId(), critiqueId);
        ratingVoteRepository.deleteById(ratingVote.getId());
        updateCritiqueAndCriticEntities(ratingVote.getCritique());
    }

    public void updateCritiqueAndCriticEntities(@NonNull Critique critique) {
        Critic critic = critique.getCritiqueOwner();

        //Update critique
        int critiqueCount = findQuantityOfVotesByCritiqueId(critique.getId());
        double critiqueRatingSum = findSumOfVotesByCritiqueId(critique.getId());
        if(critiqueCount == 0){
            critiqueCount = 1;
        }
        double critique_c = critiqueRatingSum / critiqueCount;
        double resultToCritique = EarUtils.floorNumber(1, critique_c);
        critique.setRating(resultToCritique);

        //Update critic
        int criticCount = critiqueRepository.findQuantityOfCritiquesByCriticId(critic.getId()).orElse(0);
        double criticRatingSum = critiqueRepository.findSumOfCritiquesRatingByCriticId(critic.getId()).orElse(0.0);
        double critic_c = criticRatingSum / criticCount;
        double resultToCritic = EarUtils.floorNumber(1, critic_c);
        critic.setCriticRating(resultToCritic);

    }




}
