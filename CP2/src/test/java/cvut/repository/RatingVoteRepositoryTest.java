package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.*;
import cvut.services.AppUserService;
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
import java.util.*;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)

public class RatingVoteRepositoryTest {

    @Autowired
    private RatingVoteRepository ratingVoteRepository;
    @Autowired
    private CritiqueRepository critiqueRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private FilmService filmService;
    @Autowired
    private MainRoleService mainRoleService;
    @Autowired
    private AppUserService appUserService;

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void addRatingWithSpecifiedCritique() {
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 300);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
        critiqueRepository.save(critique);

        //User with id 300....
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);

        //Assert
        Assertions.assertTrue(critique != null);
        Assertions.assertTrue(appUser != null);

        RatingVote genRatingVote = Generator.generateRating(critique, appUser);
        ratingVoteRepository.save(genRatingVote);

        //Find
        Optional<RatingVote> ratingById = ratingVoteRepository.findById(genRatingVote.getId());

        //Assert
        Assertions.assertTrue(ratingById.isPresent());
        Assertions.assertEquals(ratingById.get().getId(), genRatingVote.getId());
        Assertions.assertEquals(ratingById.get().getStars(), genRatingVote.getStars());
        Assertions.assertEquals(critique.getId(), ratingById.get().getCritique().getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void findQuantityOfVotesByCritiqueIdTest() {
        Optional<Critique> byId = critiqueRepository.findById(1L);

        //Assert
        Assertions.assertTrue(byId.isPresent());

        List<RatingVote> critiqueRatingVote = byId.get().getCritiqueRatingVote();

        Optional<Integer> quantityOfVotesByCritiqueId =
                ratingVoteRepository.findQuantityOfVotesByCritiqueId(byId.get().getId());

        //Assert
        Assertions.assertEquals(critiqueRatingVote.size(), quantityOfVotesByCritiqueId.get());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void findSumOfVotesByCritiqueIdTest() {
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 300);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
        critiqueRepository.save(critique);
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        Date date = Generator.generateDate();

        //Assert
        Assertions.assertTrue(critique != null);

        List<RatingVote> ratingVotes = new ArrayList<>();
        RatingVote ratingVote = new RatingVote(critique, 4.0, date, appUser);
        ratingVoteRepository.save(ratingVote);
        RatingVote ratingVote1 = new RatingVote(critique, 3.0, date, appUser);
        ratingVoteRepository.save(ratingVote1);
        ratingVotes.add(ratingVote);
        ratingVotes.add(ratingVote1);

        critique.setCritiqueRatingVote(ratingVotes);

        List<RatingVote> critiqueRatingVote = critique.getCritiqueRatingVote();

        Optional<Double> sumOfVotesByCritiqueId =
                ratingVoteRepository.findSumOfVotesByCritiqueId(critique.getId());

        Double sum = critiqueRatingVote.stream()
                .map(x -> x.getStars())
                .reduce(0.0, Double::sum);

        //Assert
        Assertions.assertEquals(sum, sumOfVotesByCritiqueId.get());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindTheLowestRating() {
        Query query = em.createNamedQuery("RatingVote.findTheLowestRating");
        List<Double> rating = query.getResultList();
        Assertions.assertNotNull(rating);
        Assertions.assertFalse(rating.isEmpty());
        Assertions.assertEquals(rating.size(), 1);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void testNamedQueryFindTheHighestRating() {
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 15);
        Critic critic = new Critic("Hoho", "Hehe", "hihihaha", "huhuhu", "hihilka@mail.com");
        List<Critique> critiqueList = new ArrayList<>();
        critiqueList.add(critique);
        critic.setCritiqueList(critiqueList);
        critique.setCritiqueOwner(critic);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);

        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
        critiqueRepository.save(critique);

        Date date = Generator.generateDate();
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);

        RatingVote ratingVote = new RatingVote(critique, 5.0, date, appUser);
        ratingVoteRepository.save(ratingVote);
        Query query = em.createNamedQuery("RatingVote.findTheHighestRating");
        List<Double> rating = query.getResultList();
        Assertions.assertNotNull(rating);
        Assertions.assertFalse(rating.isEmpty());
        Assertions.assertEquals(rating.get(0), 5.0);
    }

    @Test
    void testNamedQueryFindQuantityOfVotesByCritiqueId() {
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 15);
        Query query = em.createNamedQuery("RatingVote.findQuantityOfVotesByCritiqueId");
        // Set query parameters
        query.setParameter(1, critique.getId());
        List<Integer> rating = query.getResultList();
        Assertions.assertNotNull(rating);
        Assertions.assertFalse(rating.isEmpty());
    }

    @Test
    void testNamedQueryFindSumOfVotesByCritiqueId() {
        Query query = em.createNamedQuery("RatingVote.findSumOfVotesByCritiqueId");

        // Set query parameters
        query.setParameter(1, 348L);

        // Execute the query and get the results
        List<RatingVote> rating = query.getResultList();


        Assertions.assertNotNull(rating);
        Assertions.assertFalse(rating.isEmpty());
    }


}
