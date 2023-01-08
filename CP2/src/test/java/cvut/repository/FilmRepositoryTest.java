package cvut.repository;

import cvut.Application;
import cvut.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class FilmRepositoryTest {

    @Autowired
    private CritiqueRepository critiqueRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    void testNamedQueryFindMovieNamesForASpecificPeriod() {
        // Set data

        Query query = em.createNamedQuery("Film.findMovieNamesForASpecificPeriod");

        Optional<Critique> critique = critiqueRepository.findById(6L);
        Optional<Critique> critique1 = critiqueRepository.findById(1L);
        Date date1 = critique.get().getDateOfAcceptance();
        Date date2 = critique1.get().getDateOfAcceptance();
        // Set query parameters
        query.setParameter(1, date1);
        query.setParameter(2, date2);

        // Execute the query and get the results
        List<Film> films = query.getResultList();

        // Verify the results
        Assertions.assertNotNull(films);
        Assertions.assertFalse(films.isEmpty());
        assertNotNull(films.get(0).getName());
        assertNotNull(films.get(0).getId());
    }

    @Test
    void testNamedQueryFindMovieNamesCriticizedByAParticularCritic() {
        Query query = em.createNamedQuery("Film.findMovieNamesCriticizedByAParticularCritic");

        Optional<Critique> critique = critiqueRepository.findById(6L);

        Critic critic = critique.get().getCritiqueOwner();
        // Set query parameters
        query.setParameter(1, critic.getId());

        // Execute the query and get the results
        List<Film> films = query.getResultList();

        Assertions.assertNotNull(films);
        Assertions.assertFalse(films.isEmpty());
        Assertions.assertNotEquals(films.size(), 0);
        Assertions.assertNotNull(films.get(0).getName());
    }

    @Test
    void testFindMovieNamesForASpecificPeriodNNQ() {
        Optional<Critique> critique = critiqueRepository.findById(6L);
        Optional<Critique> critique1 = critiqueRepository.findById(1L);
        Date date1 = critique.get().getDateOfAcceptance();
        Date date2 = critique1.get().getDateOfAcceptance();
        Query query = em.createNamedQuery("findMovieNamesForASpecificPeriodNNQ");
        query.setParameter(1, date1);
        query.setParameter(2, date2);
        List<Film> films = query.getResultList();

        // Verify the results
        Assertions.assertNotNull(films);
        Assertions.assertFalse(films.isEmpty());
    }

    @Test
    void testFindMovieNamesCriticizedByAParticularCriticNNQ() {
        long criticId = 1330L;
        Query query = em.createNamedQuery("findMovieNamesCriticizedByAParticularCriticNNQ");
        query.setParameter(1, criticId);
        List<Film> films = query.getResultList();
        Assertions.assertNotNull(films);
        Assertions.assertNotEquals(films.size(), 0);
    }

}