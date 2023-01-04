package cvut.services;

import cvut.Application;
import cvut.config.utils.EarUtils;
import cvut.config.utils.Generator;
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
public class RatingVoteServiceImplTest {

    @Autowired
    private RatingVoteVoteServiceImpl ratingVoteServiceImpl;

    @Autowired
    private AppUserServiceImpl appUserServiceImpl;

    @Autowired
    private CritiqueServiceImpl critiqueServiceImpl;


    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRatingToCritiqueAndCritic(){
        Critique critique = critiqueServiceImpl.findById(1L);
        critique.setCritiqueState(CritiqueState.ACCEPTED);
        Critic critic = critique.getCritiqueOwner();
        AppUser appUser = Generator.generateUser();
        appUserServiceImpl.save(appUser);
        ratingVoteServiceImpl.makeVoteAndUpdateCritiqueAndCriticRatings(appUser.getUsername(), critique.getId(), 4);

        double expectedForCritique = ratingVoteServiceImpl.findSumOfVotesByCritiqueId(critique.getId())/
                ratingVoteServiceImpl.findQuantityOfVotesByCritiqueId(critique.getId());
        double expectedForCritic = critiqueServiceImpl.findSumOfCritiquesByCriticId(critic.getId())
                / critic.getCritiqueList().size();

        Assertions.assertEquals(EarUtils.floorNumber(1, expectedForCritique), critique.getRating());
        Assertions.assertEquals(EarUtils.floorNumber(1,expectedForCritic), critic.getCriticRating());

    }


    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteRatingFromCritiqueAndCritic(){
        Critique critique = critiqueServiceImpl.findById(1L);
        critique.setCritiqueState(CritiqueState.ACCEPTED);
        Critic critic = critique.getCritiqueOwner();
        AppUser appUser = Generator.generateUser();
        appUserServiceImpl.save(appUser);

        //Create
        ratingVoteServiceImpl.makeVoteAndUpdateCritiqueAndCriticRatings(appUser.getUsername(), critique.getId(), 5);


        ratingVoteServiceImpl.deleteAndUpdate(appUser.getUsername(),critique.getId());

        double expectedForCritic = critiqueServiceImpl.findSumOfCritiquesByCriticId(critic.getId())
                / critic.getCritiqueList().size();

        Assertions.assertEquals(0, critique.getRating());
        Assertions.assertEquals(EarUtils.floorNumber(1,expectedForCritic), critic.getCriticRating());
    }
}
