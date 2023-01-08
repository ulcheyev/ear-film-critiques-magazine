package cvut.generate;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Admin;
import cvut.model.Film;
import cvut.repository.AdminRepository;
import cvut.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class GenerateFilm {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    public void generate() {
        for (int i = 0; i < 100; i++) {
            Film film = Generator.generateFilm();
            filmRepository.save(film);
        }
    }

}
