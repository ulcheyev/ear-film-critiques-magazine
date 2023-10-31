package cvut.generate;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import cvut.repository.AppUserRepository;
import cvut.repository.AppUserRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class GenerateAppUser {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void generate() {
        for (int i = 0; i < 100; i++) {
            AppUser appUser = Generator.generateUser();
            appUserRepository.save(appUser);
        }
    }
}
