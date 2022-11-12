package cvut.repository;
import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CritiqueRepositoryTest {

    @Autowired
    private CritiqueRepository critiqueRepository;


    @Test
    public void findQuantityOfCritiquesByCriticIdTest() {

    }

    @Test
    public void findSumOfCritiquesRatingByCriticIdTest() {

    }






}
