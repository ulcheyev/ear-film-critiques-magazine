package cvut.repository;
import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CritiqueRepositoryTest {

    @Autowired
    private CritiqueRepository critiqueRepository;

    @Test
    public void addCritique(){
        Critique critique = Generator.generateCritique();
        critiqueRepository.save(critique);

        Critique founded = critiqueRepository.findById(critique.getId()).get();
        Assertions.assertEquals(critique.getAdmin().getId(), founded.getAdmin().getId());
    }


    @Test
    public void findByState(){
        List<Critique> founded = critiqueRepository.findAllByCritiqueState(CritiqueState.IN_PROCESSED).stream().toList();

        for(Critique critique: founded){
            Assertions.assertEquals(critique.getCritiqueState(), CritiqueState.IN_PROCESSED);
        }
    }

    @Test
    public void findByDate(){
        Critique critique = Generator.generateCritique();
        critiqueRepository.save(critique);

        Date date = critique.getDateOfAcceptance();
        List<Critique> founded = critiqueRepository.findAllByDateOfAcceptance(date).stream().toList();
        for(Critique crit: founded){
            Assertions.assertEquals(date.compareTo(crit.getDateOfAcceptance()), 0);
        }
    }



}
