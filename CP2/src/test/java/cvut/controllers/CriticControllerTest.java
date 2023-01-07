package cvut.controllers;

import cvut.config.utils.Generator;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.Film;
import cvut.model.MainRole;
import cvut.model.dto.CritiqueCreationDTO;
import cvut.repository.FilmRepository;
import cvut.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CriticControllerTest extends TestHelper{

    @Autowired
    private CritiqueServiceImpl critiqueService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CritiqueController critiqueController;

    @Autowired
    private CriticService criticService;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MainRoleService mainRoleService;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCritique_hasAccessCritiqueOwner() throws Exception {

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Dobro", 10));
        appUserService.save(critique.getCritiqueOwner());
        filmRepository.save(critique.getFilm());
        critiqueService.save(critique);

        mockMvc.perform(delete("/api/profile/"+critique.getId() + "/deleteMyCritique").with(pepaUser()))
                .andExpect(status().isPreconditionFailed());

        mockMvc.perform(delete("/api/critiques/"+critique.getId())
                        .with(pepaCriticWithUsername(critique.getCritiqueOwner().getUsername())))
                .andExpect(status().isOk());

        assertFalse(critiqueService.checkExistence(critique.getId()));
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addNewCritique_withAnonymous() throws Exception {
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);

        CritiqueCreationDTO dto = new CritiqueCreationDTO();
        dto.setFilmId(film.getId());

        dto.setTitle(Generator.generateString("dobro", 10));
        dto.setText(Generator.generateString("dobro", 100));


        mockMvc.perform(
                        post("/api/profile/createNewCritique")
                                .content(toJson(dto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .with(pepaUser()))
                .andExpect(status().isPreconditionFailed());

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addNewCritique_byCritic_critiqueIsAdded() throws Exception {

        Critic critic = Generator.generateCritic();
        appUserService.save(critic);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);

        CritiqueCreationDTO dto = new CritiqueCreationDTO();
        dto.setTitle(Generator.generateString("dobro", 10));
        dto.setText(Generator.generateString("dobro", 100));
        dto.setFilmId(film.getId());

        mockMvc.perform(
                        post("/api/profile/createNewCritique")
                                .content(toJson(dto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .with(pepaCriticWithUsername(critic.getUsername())))
                .andExpect(status().isOk());


        List<Critique> byCritiqueOwnerUsername =
                critiqueService.findByCritiqueOwnerUsername(critic.getUsername());

        Assertions.assertEquals(byCritiqueOwnerUsername.size(), 1);

    }

}
