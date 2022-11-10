package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Critic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CriticRepositoryTest {

    @Autowired
    private CriticRepository criticRepository;

    @Test
    void addCritic(){
        Critic critic = Generator.generateCritic();
        criticRepository.save(critic);
        Assertions.assertNotNull(criticRepository.findById(critic.getId()));
    }
}
