package cvut.controllers;

import cvut.config.utils.Generator;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.dto.CritiqueCreationDTO;
import cvut.repository.FilmRepository;
import cvut.services.AppUserService;
import cvut.services.CriticService;
import cvut.services.CritiqueServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CritiqueControllerTest extends TestHelper{

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

    @Test
    @WithAnonymousUser
    public void getCritiques_antonymHasNotAccess() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/api/critiques")).andReturn();
        assertEquals(0, mvcResult.getResponse().getContentLengthLong());
    }

    @Test
    @WithAnonymousUser
    public void getCritiques_userHasAccess() throws Exception {
        mockMvc
                .perform(get("/api/critiques")
                .with(pepaUser()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void getCritiqueByIdWhenCriticAndCritiqueInProcessed_returnCritique() throws Exception {

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        appUserService.save(critique.getCritiqueOwner());
        filmRepository.save(critique.getFilm());
        critiqueService.save(critique);

        final MvcResult mvcResult = mockMvc
                .perform(get("/api/critiques/"+critique.getId()).with(pepaCritic())).andReturn();
        final Critique result = readValue(mvcResult, Critique.class);
        assertEquals(result.getTitle(), critique.getTitle());
        assertEquals(result.getCritiqueState(), result.getCritiqueState());


    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void getCritiqueByIdWhenUserAndCritiqueInProcessed_preconditionFailed() throws Exception {

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        appUserService.save(critique.getCritiqueOwner());
        filmRepository.save(critique.getFilm());
        critiqueService.save(critique);
        mockMvc.perform(get("/api/critiques/"+critique.getId()).with(pepaUser()))
                .andExpect(status().isPreconditionFailed());

        assertTrue(critiqueService.checkExistence(critique.getId()));


    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCritique_hasAccessCritiqueOwner() throws Exception {

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        appUserService.save(critique.getCritiqueOwner());
        filmRepository.save(critique.getFilm());
        critiqueService.save(critique);

        mockMvc.perform(delete("/api/critiques/"+critique.getId()).with(pepaUser()))
                .andExpect(status().isPreconditionFailed());

        mockMvc.perform(delete("/api/critiques/"+critique.getId())
                        .with(pepaCriticWithUsername(critique.getCritiqueOwner().getUsername())))
                .andExpect(status().isOk());

        assertFalse(critiqueService.checkExistence(critique.getId()));
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addNewCritique_withAnonymous() throws Exception {

        CritiqueCreationDTO dto = new CritiqueCreationDTO();
        dto.setTitle(Generator.generateString("lol", 10));
        dto.setText(Generator.generateString("lol", 100));
        dto.setFilmId(1L);

        mockMvc.perform(
                post("/api/critiques/creation/critique")
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

        CritiqueCreationDTO dto = new CritiqueCreationDTO();
        dto.setTitle(Generator.generateString("lol", 10));
        dto.setText(Generator.generateString("lol", 100));
        dto.setFilmId(1L);

        mockMvc.perform(
                        post("/api/critiques/creation/critique")
                                .content(toJson(dto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .with(pepaCriticWithUsername(critic.getUsername())))
                .andExpect(status().isOk());


        List<Critique> byCritiqueOwnerUsername =
                critiqueService.findByCritiqueOwnerUsername(critic.getUsername());

        Assertions.assertEquals(byCritiqueOwnerUsername.size(), 1);

    }

//    @Test
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void searchByFilter() throws Exception {
//
//        Critique critique = Generator.generateCritique(1500);
//        critique.setTitle(Generator.generateString("Lol", 10));
//        appUserService.save(critique.getCritiqueOwner());
//        filmRepository.save(critique.getFilm());
//        critiqueService.save(critique);
//
//        CritiqueDTO dto = new CritiqueDTO();
//        dto.setTitle(critique.getTitle());
//        dto.setFilm(critique.getFilm().getName());
//        dto.setUsername(critique.getCritiqueOwner().getUsername());
//        dto.setRating(0.0D);
//
//
//        final MvcResult mvcResult = mockMvc.perform(
//                        get("/api/critiques/search/")
//                                .requestAttr("title", dto.getTitle())
//                                .with(pepaCritic()))
//                .andExpect(status().isOk()).andReturn();
//
//
//        final List<Critique> result = readValue(mvcResult,new TypeReference<List<Critique>>() {});
//        Assertions.assertTrue(result.contains(critique));
//    }

}
