package cvut.services;

import cvut.Application;
import cvut.config.utils.EarUtils;
import cvut.model.AppUser;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CritiqueService critiqueService;


    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRatingToCritiqueAndCritic(){
        double stars = 4;
        Critique critique = critiqueService.findById(1L);
        critique.setCritiqueState(CritiqueState.ACCEPTED);
        Critic critic = critique.getCritiqueOwner();

        AppUser appUser = appUserService.findById(300L);
        ratingService.makeVoteAndUpdateCritiqueAndCriticRatings(appUser.getId(), critique.getId(), stars);

        double expectedForCritique = ratingService.findSumOfVotesByCritiqueId(critique.getId())/
                ratingService.findQuantityOfVotesByCritiqueId(critique.getId());
        double expectedForCritic = critiqueService.findSumOfCritiquesByCriticId(critic.getId())
                / critic.getCritiqueList().size();

        Assertions.assertEquals(EarUtils.floorNumber(1, expectedForCritique), critique.getRating());
        Assertions.assertEquals(EarUtils.floorNumber(1,expectedForCritic), critic.getCriticRating());

    }


    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteRatingFromCritiqueAndCritic(){
        Critique critique = critiqueService.findById(1L);
        critique.setCritiqueState(CritiqueState.ACCEPTED);
        Critic critic = critique.getCritiqueOwner();
        AppUser appUser = appUserService.findById(300L);

        //Create
        ratingService.makeVoteAndUpdateCritiqueAndCriticRatings(appUser.getId(), critique.getId(), 5);


        ratingService.deleteAndUpdate(appUser.getId(),critique.getId());

        double expectedForCritique = ratingService.findSumOfVotesByCritiqueId(critique.getId())/
                ratingService.findQuantityOfVotesByCritiqueId(critique.getId());
        double expectedForCritic = critiqueService.findSumOfCritiquesByCriticId(critic.getId())
                / critic.getCritiqueList().size();


        Assertions.assertEquals(EarUtils.floorNumber(1, expectedForCritique), critique.getRating());
        Assertions.assertEquals(EarUtils.floorNumber(1,expectedForCritic), critic.getCriticRating());
    }
}
