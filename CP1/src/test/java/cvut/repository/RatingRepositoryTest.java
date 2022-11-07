package cvut.repository;


import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Critique;
import cvut.model.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)

public class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private CritiqueRepository critiqueRepository;

    @Test
    public void addRatingToCritique(){
        Critique critique = critiqueRepository.findById(Generator.generateId()).get();
        Rating rating = Generator.generateRating(critique);
        ratingRepository.save(rating);

        Rating founded = ratingRepository.findById(rating.getId()).get();
        Assertions.assertEquals(founded.getId(), rating.getId());
        Assertions.assertEquals(founded.getStars(), rating.getStars());
    }
}
