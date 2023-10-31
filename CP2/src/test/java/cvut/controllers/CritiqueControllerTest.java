package cvut.controllers;

import cvut.config.utils.Generator;
import cvut.model.Critique;
import cvut.repository.FilmRepository;
import cvut.services.AppUserService;
import cvut.services.CritiqueServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CritiqueControllerTest extends TestHelper {

    @Autowired
    private CritiqueServiceImpl critiqueService;

    @Autowired
    private AppUserService appUserService;

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
                .perform(get("/api/critiques/" + critique.getId()).with(pepaCritic())).andReturn();
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
        mockMvc.perform(get("/api/critiques/" + critique.getId()).with(pepaUser()))
                .andExpect(status().isPreconditionFailed());

        assertTrue(critiqueService.checkExistence(critique.getId()));


    }


}
