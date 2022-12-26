package cvut.repository;
import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.model.Film;
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
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CritiqueRepository critiqueRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    void testNamedQueryFindMovieNamesForASpecificPeriod(){
        Optional<Critique> critique = critiqueRepository.findById(2L);
        Optional<Critique> critique1 = critiqueRepository.findById(5L);
        Date date1 = critique.get().getDateOfAcceptance();
        Date date2 = critique1.get().getDateOfAcceptance();
        List<Film> film = filmRepository.findMovieNamesForASpecificPeriod(date1, date2);
        Assertions.assertNotNull(film);
        Assertions.assertFalse(film.isEmpty());
        Assertions.assertNotEquals(film.size(), 0);
    }

    @Test
    void testNamedQueryFindMovieNamesCriticizedByAParticularCritic(){
        List<Film> film = filmRepository.findMovieNamesCriticizedByAParticularCritic(512L);
        Assertions.assertNotNull(film);
        Assertions.assertFalse(film.isEmpty());
        Assertions.assertNotEquals(film.size(), 0);
    }

    @Test
    void testFindMovieNamesForASpecificPeriodNNQ() {
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 10);
        Critique critique1 = Generator.generateCritique(CritiqueState.ACCEPTED, 15);
        Date startDate = critique.getDateOfAcceptance();
        Date endDate = critique1.getDateOfAcceptance();
        Query query = em.createNamedQuery("findMovieNamesForASpecificPeriodNNQ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        List<Film> films = query.getResultList();

        Assertions.assertNotEquals(films.size(), 0);
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