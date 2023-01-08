package cvut.generate;


import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import cvut.model.Critic;
import cvut.repository.AppUserRepository;
import cvut.repository.CriticRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class GenerateCritic {
    @Autowired
    private CriticRepository criticRepository;

    @Test
    public void generate() {
        for (int i = 0; i < 100; i++) {
            Critic critic = Generator.generateCritic();
            criticRepository.save(critic);
        }
    }
}
