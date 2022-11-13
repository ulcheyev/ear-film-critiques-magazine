package cvut.services;

import cvut.Application;
import cvut.exception.NotFoundException;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

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


}
