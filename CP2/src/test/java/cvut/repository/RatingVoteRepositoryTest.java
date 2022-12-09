package cvut.repository;


import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import cvut.model.Critique;
import cvut.model.RatingVote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)

public class RatingVoteRepositoryTest {

    @Autowired
    private RatingVoteRepository ratingVoteRepository;
    @Autowired
    private CritiqueRepository critiqueRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    Random random = new Random();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void addRatingWithSpecifiedCritique() {
        Optional<Critique> critiqueById = critiqueRepository.findById(1L);

        //User with id 300....
        Optional<AppUser> appUserById = appUserRepository.findById(random.nextLong(200L, 300L));

        //Assert
        Assertions.assertTrue(critiqueById.isPresent());
        Assertions.assertTrue(appUserById.isPresent());

        RatingVote genRatingVote = Generator.generateRating(critiqueById.get(), appUserById.get());
        ratingVoteRepository.save(genRatingVote);

        //Find
        Optional<RatingVote> ratingById = ratingVoteRepository.findById(genRatingVote.getId());

        //Assert
        Assertions.assertTrue(ratingById.isPresent());
        Assertions.assertEquals(ratingById.get().getId(), genRatingVote.getId());
        Assertions.assertEquals(ratingById.get().getStars(), genRatingVote.getStars());
        Assertions.assertEquals(critiqueById.get().getId(), ratingById.get().getCritique().getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void findQuantityOfVotesByCritiqueIdTest(){
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
    public void findSumOfVotesByCritiqueIdTest(){
        Optional<Critique> byId = critiqueRepository.findById(1L);

        //Assert
        Assertions.assertTrue(byId.isPresent());

        List<RatingVote> critiqueRatingVote = byId.get().getCritiqueRatingVote();

        Optional<Double> sumOfVotesByCritiqueId =
                ratingVoteRepository.findSumOfVotesByCritiqueId(byId.get().getId());

        Double sum  = critiqueRatingVote.stream()
                .map(x -> x.getStars())
                .reduce(0.0, Double::sum);

        //Assert
        Assertions.assertEquals(sum, sumOfVotesByCritiqueId.get() );
    }

    @Test
    void testNamedQueryFindTheLowestRating(){
        Optional<Double> rating = ratingVoteRepository.findTheLowestRating();
        Assertions.assertNotNull(rating);
        Assertions.assertFalse(rating.isEmpty());
        Assertions.assertEquals(rating.get().byteValue(), 0.0);
    }

    @Test
    void testNamedQueryFindTheHighestRating(){
        Optional<Double> rating = ratingVoteRepository.findTheHighestRating();
        Assertions.assertNotNull(rating);
        Assertions.assertFalse(rating.isEmpty());
        Assertions.assertEquals(rating.get().byteValue(), 4.0);
    }

    @Test
    void testNamedQueryFindQuantityOfVotesByCritiqueId(){
        Optional<Integer> rating = ratingVoteRepository.findQuantityOfVotesByCritiqueId(1L);
        Assertions.assertNotNull(rating);
        Assertions.assertFalse(rating.isEmpty());
        Assertions.assertEquals(rating.get().byteValue(), 14);
    }

    @Test
    void testNamedQueryFindSumOfVotesByCritiqueId(){
        Optional<Double> rating = ratingVoteRepository.findSumOfVotesByCritiqueId(1L);
        Assertions.assertNotNull(rating);
        Assertions.assertFalse(rating.isEmpty());
        Assertions.assertEquals(rating.get().byteValue(), 29.0);
    }


}
