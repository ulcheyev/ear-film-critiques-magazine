package cvut.services;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Critic;
import cvut.model.Critique;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CritiqueService critiqueService;

    @Test
    public void addRatingToCritiqueAndCritic(){
        double stars = 3;
        Critique critique = critiqueService.findById(100L);
        Critic critic = critique.getCritiqueOwner();
        ratingService.createAndUpdate(critique, stars);

        double expectedForCritique = (ratingService.findSumOfVotesByCritiqueId(critique.getId())+stars)
                / (ratingService.findQuantityOfVotesByCritiqueId(critique.getId())+1);
        double expectedForCritic = critiqueService.findSumOfCritiquesByCriticId(critic.getId())
                / critiqueService.findQuantityOfCritiquesByCriticId(critic.getId());


        Assertions.assertEquals(expectedForCritique, critique.getCritiqueRating());
        Assertions.assertEquals(expectedForCritic, critic.getCriticRating());
    }
}