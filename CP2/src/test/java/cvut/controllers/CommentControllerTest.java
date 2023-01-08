package cvut.controllers;

import cvut.config.utils.Generator;
import cvut.model.Comment;
import cvut.model.Critique;
import cvut.model.Film;
import cvut.model.MainRole;
import cvut.model.dto.creation.CommentCreationDTO;
import cvut.services.*;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends TestHelper {

    @Autowired
    private CommentController controller;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CritiqueServiceImpl critiqueService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MainRoleService mainRoleService;


    @Test
    public void addCommentByUser_commentAdded() throws Exception {

        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        appUserService.save(critique.getCritiqueOwner());
        critique.setFilm(film);
        critiqueService.save(critique);

        CommentCreationDTO commentCreationDTO = new CommentCreationDTO();
        commentCreationDTO.setContent("earearearearear");

        mockMvc.perform(
                        post("/api/critiques/" + critique.getId())
                                .content(toJson(commentCreationDTO))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .with(pepaCriticWithUsername(critique.getCritiqueOwner().getUsername())))
                .andExpect(status().isOk());

        Assertions.assertFalse(critiqueService.findById(critique.getId()).getComments().isEmpty());
    }

    @Test
    public void deleteCommentByUser_commentDeleted() throws Exception {

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

        CommentCreationDTO commentCreationDTO = new CommentCreationDTO();
        commentCreationDTO.setContent("earearearearear");

        mockMvc.perform(
                        post("/api/critiques/" + critique.getId())
                                .content(toJson(commentCreationDTO))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .with(pepaCriticWithUsername(critique.getCritiqueOwner().getUsername())))
                .andExpect(status().isOk());

        Assertions.assertFalse(critiqueService.findById(critique.getId()).getComments().isEmpty());

        List<Comment> comments = critiqueService.findById(critique.getId()).getComments();

        mockMvc.perform(
                        delete("/api/critiques/" + critique.getId())
                                .content(toJson(commentCreationDTO))
                                .param("co_flagId", String.valueOf(comments.get(0).getId()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .with(pepaCriticWithUsername(critique.getCritiqueOwner().getUsername())))
                .andExpect(status().isOk());

        Assertions.assertTrue(critiqueService.findById(critique.getId()).getComments().isEmpty());

    }
}
