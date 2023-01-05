package cvut.controllers;

import cvut.config.utils.Generator;
import cvut.model.Critique;
import cvut.services.CritiqueServiceImpl;
import cvut.services.RatingVoteVoteServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RatingVoteControllerTest extends TestHelper{

    @Autowired
    private RatingVoteVoteServiceImpl ratingVoteVoteService;
    @Autowired
    private CritiqueServiceImpl critiqueService;

    @Autowired
    private CritiqueController critiqueController;


    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void makeVoteVyUser_VoteIsCreated() throws Exception {

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        critiqueService.save(critique);

        Assertions.assertEquals(ratingVoteVoteService.findQuantityOfVotesByCritiqueId(critique.getId()), 0);

        mockMvc
                .perform(post("/api/critiques/"+critique.getId())
                        .param("stars", "3.0")
                        .with(pepaUserWithUsername(critique.getCritiqueOwner().getUsername())))
                .andExpect(status().isOk());

        Assertions.assertEquals(ratingVoteVoteService.findQuantityOfVotesByCritiqueId(critique.getId()), 1);

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteVote_voteDeleted() throws Exception {

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        critiqueService.save(critique);

        Assertions.assertEquals(ratingVoteVoteService.findQuantityOfVotesByCritiqueId(critique.getId()), 0);

        mockMvc
                .perform(post("/api/critiques/"+critique.getId())
                        .param("stars", "3.0")
                        .with(pepaUserWithUsername(critique.getCritiqueOwner().getUsername())))
                .andExpect(status().isOk());

        Assertions.assertEquals(ratingVoteVoteService.findQuantityOfVotesByCritiqueId(critique.getId()), 1);

        mockMvc
                .perform(delete("/api/critiques/"+critique.getId())
                        .param("unvote", String.valueOf(critique.getId()))
                        .with(pepaUserWithUsername(critique.getCritiqueOwner().getUsername())))
                .andExpect(status().isOk());

        Assertions.assertEquals(ratingVoteVoteService.findQuantityOfVotesByCritiqueId(critique.getId()), 0);

    }
}
