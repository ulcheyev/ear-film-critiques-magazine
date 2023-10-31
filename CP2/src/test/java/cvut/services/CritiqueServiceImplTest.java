package cvut.services;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.*;
import cvut.model.dto.CritiqueCreationDTO;
import cvut.repository.CriticRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CritiqueServiceImplTest {

    @Autowired
    private CritiqueServiceImpl critiqueServiceImpl;
    @Autowired
    private CriticRepository criticRepository;
    @Autowired
    private FilmServiceImpl filmService;
    @Autowired
    private MainRoleService mainRoleService;
    @Autowired
    private AppUserService appUserService;

    @Test
    public void findAllByCritiqueStateTest() {

        CritiqueState critiqueState = CritiqueState.IN_PROCESSED;
        List<Critique> allByCritiqueState = critiqueServiceImpl.findAllByCritiqueState(critiqueState);
        for (Critique critique : allByCritiqueState) {
            //Assert
            Assertions.assertEquals(critiqueState, critique.getCritiqueState());
        }

        CritiqueState critiqueState2 = CritiqueState.CANCELED;
        //Assert
        Assertions.assertThrows(NotFoundException.class,
                () -> critiqueServiceImpl.findAllByCritiqueState(critiqueState2)
        );

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void findAllByFilmTest() {
        Film film = Generator.generateFilm();
        Critic critic = new Critic("Lola", "Lolovaqde", "lolowefewwefwflo", "flfplfpafd", "mdqwewefweffwefoq@gmail.com");
        criticRepository.save(critic);
        Critique critique1 = Generator.generateCritique(CritiqueState.ACCEPTED, 400);
        critique1.setFilm(film);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique1.setFilm(film);
        appUserService.save(critique1.getCritiqueOwner());
        critiqueServiceImpl.save(critique1);
        List<Critique> critiques = critiqueServiceImpl.findByFilmName(critique1.getFilm().getName());

        for (Critique critique : critiques) {
            assertThat(critique.getFilm().getName()).contains(film.getName());
        }
    }


    @Test
    public void findAllByRatingTest() {
        double rating = 0;
        List<Critique> critiques = critiqueServiceImpl.findByRating(rating);

        for (Critique critique : critiques) {
            Assertions.assertEquals(rating, critique.getRating());
        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void findAllByLastnameAndFirstnameLikeTest() {
        Critic critic = new Critic("Lola", "Lolovaqde", "lolowefewwefwflo", "flfplfpafd", "mdqwewefweffwefoq@gmail.com");
        Critique critique1 = Generator.generateCritique(CritiqueState.ACCEPTED, 400);
        List<Critique> critiqueList = new ArrayList<>();
        critiqueList.add(critique1);
        critic.setCritiqueList(critiqueList);
        critique1.setCritiqueOwner(critic);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);
        Film film = critique1.getFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(critique1.getFilm());
        appUserService.save(critique1.getCritiqueOwner());
        critiqueServiceImpl.save(critique1);

        String firstname = critic.getFirstname();
        String lastname = critic.getLastname();

        List<Critique> critiques1 = critiqueServiceImpl.findByCriticsLastnameAndFirstname(firstname, lastname);

        for (Critique critique : critiques1) {
            Assertions.assertEquals(firstname, critique.getCritiqueOwner().getFirstname());
            Assertions.assertEquals(lastname, critique.getCritiqueOwner().getLastname());
        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCritiqueStateTest() {
        Critique critique = critiqueServiceImpl.findById(163L);
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.IN_PROCESSED);

        critiqueServiceImpl.updateCritiqueState(critique.getId(), CritiqueState.CANCELED);
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.CANCELED);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCritiqueTest() {
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);

        Critique critique1 = Generator.generateCritique(CritiqueState.IN_PROCESSED, 400);
        critique1.setFilm(film);
        appUserService.save(critique1.getCritiqueOwner());
        critiqueServiceImpl.save(critique1);

        CritiqueCreationDTO critiqueDTO = new CritiqueCreationDTO();
        critiqueDTO.setText(Generator.generateString("d", 342));
        critiqueDTO.setTitle(Generator.generateString("f", 100));

        critiqueDTO.setFilmId(film.getId());

        //Change
        critiqueServiceImpl.updateCritique(critique1.getId(), critiqueDTO);

        Assertions.assertEquals(critique1.getText(), critiqueDTO.getText());
        Assertions.assertEquals(critique1.getTitle(), critiqueDTO.getTitle());

        //Try to update with min text length
        String textToChange_min = Generator.generateString("Test", 50);
        critiqueDTO.setText(textToChange_min);
        //Change
        assertThrows(ValidationException.class, () -> {
            critiqueServiceImpl.updateCritique(critique1.getId(), critiqueDTO);
        });

        //Try to update with min title length
        String titleToChange_min = Generator.generateString("Test", 3);
        critiqueDTO.setTitle(titleToChange_min);
        //Change
        assertThrows(ValidationException.class, () -> {
            critiqueServiceImpl.updateCritique(critique1.getId(), critiqueDTO);
        });

        //Try to update with max text length
        String textToChange_max = Generator.generateString("Test", 5000);
        critiqueDTO.setText(textToChange_max);
        //Change
        assertThrows(ValidationException.class, () -> {
            critiqueServiceImpl.updateCritique(critique1.getId(), critiqueDTO);
        });

        //Try to update with max title length
        String titleToChange_max = Generator.generateString("Test", 1500);
        critiqueDTO.setTitle(titleToChange_max);
        //Change
        assertThrows(ValidationException.class, () -> {
            critiqueServiceImpl.updateCritique(critique1.getId(), critiqueDTO);
        });
    }

}