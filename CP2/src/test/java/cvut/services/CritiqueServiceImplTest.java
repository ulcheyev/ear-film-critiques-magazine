package cvut.services;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.model.Film;
import cvut.model.dto.CritiqueCreationDTO;
import cvut.repository.CriticRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Test
    public void findAllByCritiqueStateTest(){

        CritiqueState critiqueState = CritiqueState.IN_PROCESSED;
        List<Critique> allByCritiqueState = critiqueServiceImpl.findAllByCritiqueState(critiqueState);
        for(Critique critique: allByCritiqueState){
            //Assert
            Assertions.assertEquals(critiqueState, critique.getCritiqueState());
        }

        CritiqueState critiqueState2 = CritiqueState.CANCELED;
        //Assert
        Assertions.assertThrows(NotFoundException.class,
                ()-> critiqueServiceImpl.findAllByCritiqueState(critiqueState2)
        );

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void findAllByFilmTest(){
        Film film = Generator.generateFilm();
        Critic critic = new Critic("Lola", "Lolovaqde", "lolowefewwefwflo", "flfplfpafd", "mdqwewefweffwefoq@gmail.com");
        criticRepository.save(critic);
        Critique critique1 = Generator.generateCritique(CritiqueState.ACCEPTED, 400);
        critique1.setFilm(film);
        critiqueServiceImpl.save(critique1);
        List<Critique> critiques = critiqueServiceImpl.findByFilmName(critique1.getFilm().getName());

        for(Critique critique: critiques){
            assertThat(critique.getFilm().getName()).contains(film.getName());
        }
    }


    @Test
    public void findAllByRatingTest(){
        double rating = 0;
        List<Critique> critiques = critiqueServiceImpl.findByRating(rating);

        for(Critique critique: critiques){
            Assertions.assertEquals(rating, critique.getRating());
        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void findAllByLastnameAndFirstnameLikeTest(){
//        Critic critic = new Critic("Lola", "Lolovaqde", "lolowefewwefwflo", "flfplfpafd", "mdqwewefweffwefoq@gmail.com");
//        Critique critique1 = Generator.generateCritique(CritiqueState.ACCEPTED, 400);
//        List<Critique> critiqueList = new ArrayList<>();
//        critiqueList.add(critique1);
//        critic.setCritiqueList(critiqueList);
//        critique1.setCritiqueOwner(critic);
//        critiqueServiceImpl.save(critique1);
//        criticRepository.save(critic);
//        Optional<Critic> critic = criticRepository.findById(2680L);
//        List<Critique> critiques = critiqueServiceImpl.findByCritiqueOwnerId(critic.get().getId());
//
//        Assertions.assertFalse(critiques.isEmpty());
//        Assertions.assertTrue(critiques.size() > 1);
//
//        String firstname = critiques.get(0).getCritiqueOwner().getFirstname();
//        String lastname = critiques.get(0).getCritiqueOwner().getLastname();
//
//        List<Critique> critiques1 = critiqueServiceImpl.findByCriticsLastnameAndFirstname(firstname, lastname);
//
//        for(Critique critique: critiques1){
//            Assertions.assertEquals(firstname, critique.getCritiqueOwner().getFirstname());
//            Assertions.assertEquals(lastname, critique.getCritiqueOwner().getLastname());
//        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCritiqueStateTest(){
        Critique critique = critiqueServiceImpl.findById(163L);
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.IN_PROCESSED);

        critiqueServiceImpl.updateCritiqueState(critique.getId(), CritiqueState.CANCELED);
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.CANCELED);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCritiqueTest(){
//        Long id = 40L;
//        Critique critique = critiqueServiceImpl.findById(id);
//
////        CritiqueCreationDTO critique1 = new CritiqueCreationDTO();
//        Critique critique1 = Generator.generateCritique(CritiqueState.SENT_FOR_CORRECTIONS, 400);
//        critique1.setText(Generator.generateString("d",342));
//        critique1.setTitle(Generator.generateString("f",100));
//        Film film = filmService.findById(1L);
//        critique1.setFilm(film);
//
//        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.ACCEPTED);
//
//        //Change
//        critiqueServiceImpl.updateCritique(id, critique1);
//
//        Assertions.assertEquals(critique1.getText(), critique.getText());
//        Assertions.assertEquals(critique1.getTitle(), critique.getTitle());
//
//        //Try to update with min text length
//        String textToChange_min = Generator.generateString("Test", 50);
//        critique1.setText(textToChange_min);
//        //Change
//        assertThrows(ValidationException.class, () -> {
//            critiqueServiceImpl.updateCritique(id, critique1);
//        });
//
//        //Try to update with min title length
//        String titleToChange_min = Generator.generateString("Test", 3);
//        critique1.setTitle(titleToChange_min);
//        //Change
//        assertThrows(ValidationException.class, () -> {
//            critiqueServiceImpl.updateCritique(id, critique1);
//        });
//
//        //Try to update with max text length
//        String textToChange_max = Generator.generateString("Test", 5000);
//        critique1.setText(textToChange_max);
//        //Change
//        assertThrows(ValidationException.class, () -> {
//            critiqueServiceImpl.updateCritique(id, critique1);
//        });
//
//        //Try to update with max title length
//        String titleToChange_max = Generator.generateString("Test", 1500);
//        critique1.setTitle(titleToChange_max);
//        //Change
//        assertThrows(ValidationException.class, () -> {
//            critiqueServiceImpl.updateCritique(id, critique1);
//        });
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void correctCritiqueAfterCreateRemarksTest() {
//        Long id = 12L;
//
//        Critique critique = critiqueServiceImpl.findById(id);
//        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.ACCEPTED);
//
//        String textToChange = Generator.generateString("ef3w", 100);
//        String titleToChange = Generator.generateString("523f", 10);
//
//        //Wrong state
//        assertThrows(ValidationException.class, () -> {
//            critiqueServiceImpl.correctCritiqueAfterCreateRemarks(id, titleToChange, textToChange);
//        });
//
//        //Right state
//        critique.setCritiqueState(CritiqueState.SENT_FOR_CORRECTIONS);
//        critiqueServiceImpl.correctCritiqueAfterCreateRemarks(id, titleToChange, textToChange);
//
//        //Assert
//        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.CORRECTED);
    }

    }