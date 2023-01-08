package cvut.generate;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Film;
import cvut.model.FilmRole;
import cvut.model.MainRole;
import cvut.repository.FilmRepository;
import cvut.repository.MainRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Random;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class GenerateMainRoles {

    @Autowired
    private MainRoleRepository mainRoleRepository;

    @Test
    public void generate() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            MainRole mainRole = new MainRole();
            mainRole.setFilmRole(FilmRole.values()[random.nextInt(FilmRole.values().length)]);
            mainRole.setFirstname(Generator.faker.name().firstName());
            mainRole.setLastname(Generator.faker.name().lastName());
            mainRoleRepository.save(mainRole);
        }
    }
}
