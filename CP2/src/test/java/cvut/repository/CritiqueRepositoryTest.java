package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.*;
import cvut.services.AppUserService;
import cvut.services.CritiqueServiceImpl;
import cvut.services.FilmService;
import cvut.services.MainRoleService;
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
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MainRoleService mainRoleService;


    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindByFilmIdAndRating() {
        // Set the minimum rating and film ID for the criteria
        Critic critic = new Critic("Lola", "Lolova", "asqweqrqrdasdas", "flfplfpafd", "erqweeqwewerwe@gmail.com");
        Film film = Generator.generateFilm();
        Critique critique_new = new Critique("fmkwmfkmlkmlmlwfw", "wkfmwefinwefjwenfdjnwdjcnjandcljansdljnqjldnqlwdnqndjlqndlnasldnasdnjlsdawkuhbbibnonohoijoijipjijjjijijpjjhuibefwfmwofeamfkmakldcmsklmlksdmvlkwekfmwkefmklwmfklmdslkmksmcksmcksmckmscmsmclkmvmafasnvanvlansvlnalsdnvlasvnlknvklnrvlnvlkqnwvkmwvklmdklmvklasdvadsamgeanrgoqngo[qejrogija[oefgj'lakdmfvkl'aemgklqaojrgoargeqibvvfwlf", film, critic);

        critique_new.setRating(4.0);
        critique_new.setCritiqueState(CritiqueState.IN_PROCESSED);

        critique_new.setFilm(film);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        appUserService.save(critique_new.getCritiqueOwner());

        critiqueRepository.save(critique_new);

        double ratingForQuery = 3.0;
        // Create a TypedQuery for the named query "Critique.findByFilmIdAndRating"
        TypedQuery<Critique> query = em.createNamedQuery("Critique.findByFilmIdAndRating", Critique.class);

        // Set the parameters for the named query
        query.setParameter(1, ratingForQuery);
        query.setParameter(2, critique_new.getFilm().getId());

        // Execute the query and get the result
        List<Critique> result = query.getResultList();

        // Verify that the result is what we expect
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getFilm().getId(), critique_new.getFilm().getId());
        assertTrue(critique_new.getRating() > ratingForQuery);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindQuantityOfCritiquesByCriticId() {
        Critic critic = new Critic("Lola", "Lolova", "asdasfafldas", "flfplfpafd", "erwk12erwe@gmail.com");
        Critique critique1 = Generator.generateCritique(CritiqueState.ACCEPTED, 15);
        Critique critique2 = Generator.generateCritique(CritiqueState.ACCEPTED, 10);

        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);
        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique1.setFilm(film);
        critique2.setFilm(film);

        critique1.setCritiqueOwner(critic);
        critique2.setCritiqueOwner(critic);
        appUserService.save(critique1.getCritiqueOwner());


        critiqueRepository.save(critique1);
        critiqueRepository.save(critique2);
        List<Critique> critiqueList = new ArrayList<>();

        critiqueList.add(critique1);
        critiqueList.add(critique2);

        critic.setCritiqueList(critiqueList);

        TypedQuery<Long> query =
                em.createNamedQuery("Critique.findQuantityOfCritiquesByCriticId", Long.class);

        // Set the parameter for the named query
        Long id = critic.getId();
        query.setParameter(1, id);

        // Execute the query and get the result
        Long result = query.getSingleResult();

        Assertions.assertNotNull(result);
        assertEquals(result, critic.getCritiqueList().size());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindSumOfCritiquesRatingByCriticId() {
        Critic critic = new Critic("Lola", "Lolova", "lomnnknklolo", "flfplfpafd", "mdhgjqfoq@gmail.com");
        criticRepository.save(critic);
        Critique critique1 = Generator.generateCritique(CritiqueState.ACCEPTED, 15);
        Critique critique2 = Generator.generateCritique(CritiqueState.ACCEPTED, 10);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);
        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique1.setFilm(film);
        critique2.setFilm(film);

        critique1.setCritiqueOwner(critic);
        critique1.setCritiqueOwner(critic);
        critique2.setCritiqueOwner(critic);
        critique1.setRating(2.0);
        critique2.setRating(3.4);
        critiqueRepository.save(critique1);
        critiqueRepository.save(critique2);
        List<Critique> critiqueList = new ArrayList<>();
        critiqueList.add(critique1);
        critiqueList.add(critique2);
        critic.setCritiqueList(critiqueList);
        TypedQuery<Double> query = em.createNamedQuery("Critique.findSumOfCritiquesRatingByCriticId", Double.class);

        // Set the parameter for the named query
        query.setParameter(1, critic.getId());

        // Execute the query and get the result
        Double result = query.getSingleResult();
        Assertions.assertNotNull(result);
        assertFalse(result == 0);
        assertTrue(result > 2);
    }

    @Test
    void testNamedQueryFindAllByCritiqueState() {
        List<Critique> critiques = critiqueRepository.findAllByCritiqueState(cvut.model.CritiqueState.SENT_FOR_CORRECTIONS);
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertTrue(critiques.size() > 1);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike() {
        Critic critic = new Critic("Lola", "Lolova", "lololjlklo", "flfplfpafd", "mjjhkjdqfoq@gmail.com");
        Critique critique1 = Generator.generateCritique(CritiqueState.ACCEPTED, 15);
        Critique critique2 = Generator.generateCritique(CritiqueState.ACCEPTED, 10);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);
        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique1.setFilm(film);
        critique2.setFilm(film);
        critique1.setCritiqueOwner(critic);
        critique2.setCritiqueOwner(critic);
        appUserService.save(critique1.getCritiqueOwner());
        critiqueRepository.save(critique1);
        critiqueRepository.save(critique2);
        List<Critique> critiqueList1 = new ArrayList<>();
        critiqueList1.add(critique1);
        critiqueList1.add(critique2);
        critic.setCritiqueList(critiqueList1);

        Query query = em.createNamedQuery("Critique.findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike");
        query.setParameter(1, critic.getFirstname());
        query.setParameter(2, critic.getLastname());
        List<Critique> critiqueList = query.getResultList();
        Assertions.assertNotNull(critiqueList);
        assertFalse(critiqueList.isEmpty());
        assertTrue(critiqueList.size() > 1);
    }

    @Test
    void testNamedQueryFindAllByFilm_NameLike() {
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 300);

        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);
        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
        critiqueRepository.save(critique);
        String filmName = critique.getFilm().getName();
        List<Critique> critiques = critiqueRepository.findAllByFilm_NameLike(filmName);
        Assertions.assertNotNull(critiques);
        assertFalse(critiques.isEmpty());
        assertEquals(critiques.size(), 1);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindAllByRating() {
        Query query = em.createNamedQuery("Critique.findAllByRating");
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 400);
        critique.setRating(4.0);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
        critiqueRepository.save(critique);
        query.setParameter(1, 4.0);
        List<Critique> critiqueList = query.getResultList();
        Assertions.assertNotNull(critiqueList);
        assertFalse(critiqueList.isEmpty());
        assertTrue(critiqueList.size() > 1);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindAllByDateOfAcceptance() {
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 300);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
        critiqueRepository.save(critique);
        Query query = em.createNamedQuery("Critique.findAllByDateOfAcceptance");
        query.setParameter(1, critique.getDateOfAcceptance());
        List<Critique> critiqueList = query.getResultList();
        Assertions.assertNotNull(critiqueList);
        assertFalse(critiqueList.isEmpty());
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
        assertNotEquals(critiques.size(), 0);
        assertNotNull(critiques);
    }

}
