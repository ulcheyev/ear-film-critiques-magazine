package cvut.repository;

import cvut.Application;
import cvut.model.Critic;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CritiqueRepositoryTest {

    @Autowired
    private CritiqueRepository critiqueRepository;
    
    @Autowired
    private CriticRepository criticRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void findQuantityOfCritiquesByCriticIdTest() {
        Optional<Critic> criticbyId = criticRepository.findById(315L);

        //Assert
        assertFalse(criticbyId.isEmpty());

        List<Critique> critiqueList = criticbyId.get().getCritiqueList();
        Optional<Integer> quantityOfCritiquesByCriticId = critiqueRepository.findQuantityOfCritiquesByCriticId(criticbyId.get().getId());

        //Assert
        assertEquals(critiqueList.size(), quantityOfCritiquesByCriticId.get());
    }

    @Test
    public void findSumOfCritiquesRatingByCriticIdTest() {
        Optional<Critic> criticbyId = criticRepository.findById(315L);

        //Assert
        assertFalse(criticbyId.isEmpty());

        List<Critique> critiqueList = criticbyId.get().getCritiqueList();
        Double sum  = critiqueList.stream()
                .map(x -> x.getRating())
                .reduce(0.0, Double::sum);

        Optional<Double> sumOfCritiquesRatingByCriticId = critiqueRepository.findSumOfCritiquesRatingByCriticId(criticbyId.get().getId());
        //Assert
        assertEquals(sum, sumOfCritiquesRatingByCriticId.orElse(0.0));
    }

    @Test
    void testNamedQueryFindByFilmIdAndRating(){
        List<Critique> critiques = critiqueRepository.findByFilmIdAndRating(1, 10L);
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindQuantityOfCritiquesByCriticId(){
        Optional<Integer> critiques = critiqueRepository.findQuantityOfCritiquesByCriticId(816L);
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertEquals(critiques.get(), 1);
    }

    @Test
    void testNamedQueryFindSumOfCritiquesRatingByCriticId(){
        Optional<Double> critiques = critiqueRepository.findSumOfCritiquesRatingByCriticId(512L);
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertEquals(critiques.get(), 4.2);
    }

    @Test
    void testNamedQueryFindAllByCritiqueState(){
        List<Critique> critiques = critiqueRepository.findAllByCritiqueState(cvut.model.CritiqueState.SENT_FOR_CORRECTIONS);
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike(){
        List<Critique> critiques = critiqueRepository.findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike("Kuhlman", "Lia");
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindAllByFilm_NameLike(){
        List<Critique> critiques = critiqueRepository.findAllByFilm_NameLike("Regional Infrastructure Orchestrator");
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindAllByRating(){
        List<Critique> critiques = critiqueRepository.findAllByRating(4.2);
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertEquals(critiques.size(), 1);
    }

    @Test
    void testNamedQueryFindAllByDateOfAcceptance(){
        Optional<Critique> critique = critiqueRepository.findById(2L);
        Date date = critique.get().getDateOfAcceptance();
        List<Critique> critiques = critiqueRepository.findAllByDateOfAcceptance(date);
        Assertions.assertNotNull(critiques);
        assertEquals(critiques.get(0).getDateOfAcceptance(), date);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testFindByFilmIdAndRatingNNQ() {
        long filmId = 206L;
        double rating = -1;
        Query query = em.createNamedQuery("findByFilmIdAndRatingNNQ");
        query.setParameter(1, rating);
        query.setParameter(2, filmId);
        List<Critique> critiques = query.getResultList();
        assertFalse(critiques.isEmpty());
        for (Critique critique : critiques) {
            assertTrue(critique.getRating() > rating);
            assertEquals(filmId, critique.getFilm().getId()); //nefunguje, protože .getFilm() = null
            assertEquals(CritiqueState.IN_PROCESSED, critique.getCritiqueState());
        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testFindAllByCritiqueStateNNQ() {
        String critiqueState = "IN_PROCESSED";
        Query query = em.createNamedQuery("findAllByCritiqueStateNNQ");
        query.setParameter(1, critiqueState);
        List<Critique> critiques = query.getResultList();
        assertFalse(critiques.isEmpty());
        for (Critique critique : critiques) {
            assertEquals(critiqueState, (critique.getCritiqueState()).toString());
        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testFindAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLikeNNQ() {
        String lastnameLike = "Aber%";
        String firstnameLike = "Orv%";
        Query query = em.createNamedQuery("findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLikeNNQ");
        query.setParameter(1, lastnameLike);
        query.setParameter(2, firstnameLike);
        List<Critique> critiques = query.getResultList();
        assertFalse(critiques.isEmpty());
        for (Critique critique : critiques) {
//            assertTrue(critique.getCritiqueOwner().getLastname().matches(lastnameLike));
//            assertTrue(critique.getCritiqueOwner().getFirstname().matches(firstnameLike));
        }
    }

}
