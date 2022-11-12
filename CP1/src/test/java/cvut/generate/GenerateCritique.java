package cvut.generate;


import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import cvut.model.Critique;
import cvut.repository.AppUserRepository;
import cvut.repository.CritiqueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class GenerateCritique {
    @Autowired
    private CritiqueRepository critiqueRepository;

    @Test
    public void generate(){
        for(int i = 0; i < 100; i++) {
            Critique critique = Generator.generateCritique();
            critiqueRepository.save(critique);
        }
    }
}
