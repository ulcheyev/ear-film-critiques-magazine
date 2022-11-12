package cvut.services;

import cvut.Application;
import cvut.config.utils.EarUtils;
import cvut.model.AppUser;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class RatingServiceTest {

//    @Autowired
//    private RatingService ratingService;
//
//    @Autowired
//    private AppUserRepository appUserRepository;
//
//    @Autowired
//    private CritiqueService critiqueService;
//
//    //TODO proverit, esli useri odinakovie. Rating perepisivaetsya.
//    @Test
//    public void addRatingToCritiqueAndCritic(){
//        double stars = 4;
//        Critique critique = critiqueService.findById(15L);
//        Critic critic = critique.getCritiqueOwner();
//        AppUser appUser = appUserRepository.findById(51L).get();
//        ratingService.createAndUpdate(appUser,critique, stars);
//
//        double expectedForCritique = ratingService.findSumOfVotesByCritiqueId(critique.getId())
//                / ratingService.findQuantityOfVotesByCritiqueId(critique.getId());
//        double expectedForCritic = critiqueService.findSumOfCritiquesByCriticId(critic.getId())
//                / critiqueService.findQuantityOfCritiquesByCriticId(critic.getId());
//
//
//        Assertions.assertEquals(EarUtils.floorNumber(1, expectedForCritique), critique.getCritiqueRating());
//        Assertions.assertEquals(EarUtils.floorNumber(1,expectedForCritic), critic.getCriticRating());
//    }
//
//    //TODO proverit, esli rating udalislya iz tabulky
//    @Test
//    public void deleteRatingFromCritiqueAndCritic(){
//        Critique critique = critiqueService.findById(15L);
//        Critic critic = critique.getCritiqueOwner();
//        AppUser appUser = appUserRepository.findById(51L).get();
//        ratingService.deleteAndUpdate(appUser,critique);
//
//        double expectedForCritique = ratingService.findSumOfVotesByCritiqueId(critique.getId())
//                / ratingService.findQuantityOfVotesByCritiqueId(critique.getId());
//        double expectedForCritic = critiqueService.findSumOfCritiquesByCriticId(critic.getId())
//                / critiqueService.findQuantityOfCritiquesByCriticId(critic.getId());
//
//
//        Assertions.assertEquals(EarUtils.floorNumber(1, expectedForCritique), critique.getCritiqueRating());
//        Assertions.assertEquals(EarUtils.floorNumber(1,expectedForCritic), critic.getCriticRating());
//    }
}
