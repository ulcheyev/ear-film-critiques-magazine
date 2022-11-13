package cvut.repository;

import cvut.Application;
import cvut.model.Critic;
import cvut.model.Critique;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CritiqueRepositoryTest {

    @Autowired
    private CritiqueRepository critiqueRepository;
    
    @Autowired
    private CriticRepository criticRepository;


    @Test
    public void findQuantityOfCritiquesByCriticIdTest() {
        Optional<Critic> criticbyId = criticRepository.findById(315L);

        //Assert
        Assertions.assertFalse(criticbyId.isEmpty());

        List<Critique> critiqueList = criticbyId.get().getCritiqueList();
        Optional<Integer> quantityOfCritiquesByCriticId = critiqueRepository.findQuantityOfCritiquesByCriticId(criticbyId.get().getId());

        //Assert
        Assertions.assertEquals(critiqueList.size(), quantityOfCritiquesByCriticId.get());
    }

    @Test
    public void findSumOfCritiquesRatingByCriticIdTest() {
        Optional<Critic> criticbyId = criticRepository.findById(315L);

        //Assert
        Assertions.assertFalse(criticbyId.isEmpty());

        List<Critique> critiqueList = criticbyId.get().getCritiqueList();
        Double sum  = critiqueList.stream()
                .map(x -> x.getRating())
                .reduce(0.0, Double::sum);

        Optional<Double> sumOfCritiquesRatingByCriticId = critiqueRepository.findSumOfCritiquesRatingByCriticId(criticbyId.get().getId());
        //Assert
        Assertions.assertEquals(sum, sumOfCritiquesRatingByCriticId.orElse(0.0));
    }






}
