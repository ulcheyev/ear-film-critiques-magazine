package cvut.services;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CritiqueServiceTest {

    @Autowired
    private CritiqueService critiqueService;

    @Test
    public void findAllByCritiqueStateTest(){

        CritiqueState critiqueState = CritiqueState.IN_PROCESSED;
        List<Critique> allByCritiqueState = critiqueService.findAllByCritiqueState(critiqueState);
        for(Critique critique: allByCritiqueState){
            //Assert
            Assertions.assertEquals(critiqueState, critique.getCritiqueState());
        }

        CritiqueState critiqueState2 = CritiqueState.CANCELED;
        //Assert
        Assertions.assertThrows(NotFoundException.class,
                ()->critiqueService.findAllByCritiqueState(critiqueState2)
        );

    }

    @Test
    public void findAllByFilmTest(){
        String filmName = "National Optimization";
        List<Critique> critiques = critiqueService.findByFilm(filmName);

        for(Critique critique: critiques){
            assertThat(critique.getFilm().getName()).contains(filmName);
        }
    }


    @Test
    public void findAllByRatingTest(){
        double rating = 0;
        List<Critique> critiques = critiqueService.findByRating(rating);

        for(Critique critique: critiques){
            Assertions.assertEquals(rating, critique.getRating());
        }
    }

    @Test
    public void findAllByLastnameAndFirstnameLikeTest(){
        long id = 502L;

        List<Critique> critiques1 = critiqueService.findByCritiqueOwnerId(id);
        Assertions.assertFalse(critiques1.isEmpty());
        Assertions.assertTrue(critiques1.size() == 1);

        String firstname = critiques1.get(0).getCritiqueOwner().getFirstname();
        String lastname = critiques1.get(0).getCritiqueOwner().getLastname();

        List<Critique> critiques = critiqueService.findByCriticsLastnameAndFirstname(firstname, lastname);

        for(Critique critique: critiques){
            Assertions.assertEquals(firstname, critique.getCritiqueOwner().getFirstname());
            Assertions.assertEquals(lastname, critique.getCritiqueOwner().getLastname());
        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCritiqueStateTest(){
        Long id = 40L;
        Critique critique = critiqueService.findById(40L);
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.IN_PROCESSED);

        critiqueService.updateCritiqueState(id, CritiqueState.CANCELED);
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.CANCELED);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCritiqueTest(){
        Long id = 40L;
        Critique critique = critiqueService.findById(40L);

        String textToChange = Generator.generateString("Test", 400);
        String titleToChange = Generator.generateString("Ear@e", 20);
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.IN_PROCESSED);

        //Change
        critiqueService.updateCritique(id, titleToChange, textToChange);

        Assertions.assertEquals(textToChange, critique.getText());
        Assertions.assertEquals(titleToChange, critique.getTitle());

        //Try to update with min text length
        String textToChange_min = Generator.generateString("Test", 50);

        //Change
        assertThrows(ValidationException.class, () -> {
            critiqueService.updateCritique(id, titleToChange, textToChange_min);
        });

        //Try to update with min title length
        String titleToChange_min = Generator.generateString("Test", 3);

        //Change
        assertThrows(ValidationException.class, () -> {
            critiqueService.updateCritique(id, titleToChange_min, textToChange);
        });

        //Try to update with max text length
        String textToChange_max = Generator.generateString("Test", 5000);

        //Change
        assertThrows(ValidationException.class, () -> {
            critiqueService.updateCritique(id, titleToChange, textToChange_max);
        });

        //Try to update with max title length
        String titleToChange_max = Generator.generateString("Test", 1500);

        //Change
        assertThrows(ValidationException.class, () -> {
            critiqueService.updateCritique(id, titleToChange_max, textToChange);
        });
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void correctCritiqueAfterCreateRemarksTest() {
        Long id = 12L;

        Critique critique = critiqueService.findById(id);
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.IN_PROCESSED);

        String textToChange = Generator.generateString("ef3w", 100);
        String titleToChange = Generator.generateString("523f", 10);

        //Wrong state
        assertThrows(ValidationException.class, () -> {
            critiqueService.correctCritiqueAfterCreateRemarks(id, titleToChange, textToChange);
        });

        //Right state
        critique.setCritiqueState(CritiqueState.SENT_FOR_CORRECTIONS);
        critiqueService.correctCritiqueAfterCreateRemarks(id, titleToChange, textToChange);

        //Assert
        Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.CORRECTED);
    }







}
