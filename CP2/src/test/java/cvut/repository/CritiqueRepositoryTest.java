package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Test
    void testNamedQueryFindByFilmIdAndRating(){
        List<Critique> critiques = critiqueRepository.findByFilmIdAndRating(1, 10L);
        Assertions.assertNotNull(critiques);
        Assertions.assertFalse(critiques.isEmpty());
        Assertions.assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindQuantityOfCritiquesByCriticId(){
        Optional<Integer> critiques = critiqueRepository.findQuantityOfCritiquesByCriticId(816L);
        Assertions.assertNotNull(critiques);
        Assertions.assertFalse(critiques.isEmpty());
        Assertions.assertEquals(critiques.get(), 1);
    }

    @Test
    void testNamedQueryFindSumOfCritiquesRatingByCriticId(){
        Optional<Double> critiques = critiqueRepository.findSumOfCritiquesRatingByCriticId(512L);
        Assertions.assertNotNull(critiques);
        Assertions.assertFalse(critiques.isEmpty());
        Assertions.assertEquals(critiques.get(), 4.2);
    }

    @Test
    void testNamedQueryFindAllByCritiqueState(){
        List<Critique> critiques = critiqueRepository.findAllByCritiqueState(cvut.model.CritiqueState.SENT_FOR_CORRECTIONS);
        Assertions.assertNotNull(critiques);
        Assertions.assertFalse(critiques.isEmpty());
        Assertions.assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike(){
        List<Critique> critiques = critiqueRepository.findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike("Kuhlman", "Lia");
        Assertions.assertNotNull(critiques);
        Assertions.assertFalse(critiques.isEmpty());
        Assertions.assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindAllByFilm_NameLike(){
        List<Critique> critiques = critiqueRepository.findAllByFilm_NameLike("Regional Infrastructure Orchestrator");
        Assertions.assertNotNull(critiques);
        Assertions.assertFalse(critiques.isEmpty());
        Assertions.assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindAllByRating(){
        List<Critique> critiques = critiqueRepository.findAllByRating(4.2);
        Assertions.assertNotNull(critiques);
        Assertions.assertFalse(critiques.isEmpty());
        Assertions.assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindAllByDateOfAcceptance(){
        Optional<Critique> critique = critiqueRepository.findById(2L);
        Date date = critique.get().getDateOfAcceptance();
        List<Critique> critiques = critiqueRepository.findAllByDateOfAcceptance(date);
        Assertions.assertNotNull(critiques);
        Assertions.assertEquals(critiques.get(0).getDateOfAcceptance(), date);
    }



}
