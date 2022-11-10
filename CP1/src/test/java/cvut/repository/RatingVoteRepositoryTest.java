package cvut.repository;


import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Critique;
import cvut.model.RatingVote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)

public class RatingVoteRepositoryTest {

    @Autowired
    private RatingVoteRepository ratingVoteRepository;
    @Autowired
    private CritiqueRepository critiqueRepository;

    @Test
    public void addRatingToCritique() {
        Critique critique = critiqueRepository.findById(Generator.generateId()).get();
        RatingVote ratingVote = Generator.generateRating(critique);
        ratingVoteRepository.save(ratingVote);

        RatingVote founded = ratingVoteRepository.findById(ratingVote.getId()).get();
        Assertions.assertEquals(founded.getId(), ratingVote.getId());
        Assertions.assertEquals(founded.getStars(), ratingVote.getStars());

    }
}
