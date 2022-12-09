package cvut.repository;
import com.github.javafaker.App;
import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import cvut.model.Critique;
import cvut.model.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

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

}