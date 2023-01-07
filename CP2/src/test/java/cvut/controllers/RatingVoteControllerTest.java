package cvut.controllers;

import cvut.config.utils.Generator;
import cvut.model.Critique;
import cvut.model.Film;
import cvut.model.MainRole;
import cvut.services.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RatingVoteControllerTest extends TestHelper{

    @Autowired
    private RatingVoteVoteServiceImpl ratingVoteVoteService;
    @Autowired
    private CritiqueServiceImpl critiqueService;

    @Autowired
    private CritiqueController critiqueController;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MainRoleService mainRoleService;

    @Autowired
    private AppUserService appUserService;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void makeVoteVyUser_VoteIsCreated() throws Exception {

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
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
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
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
