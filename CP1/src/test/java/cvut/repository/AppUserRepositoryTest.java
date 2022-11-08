package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class AppUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void addUser(){
        AppUser appUser = Generator.generateUser();
        userRepository.save(appUser);
        Assertions.assertNotNull(userRepository.findById(appUser.getId()));
    }

    @Test
    void findByUsername(){
        AppUser appUser = Generator.generateUser();
        userRepository.save(appUser);
        AppUser foundedAppUser = userRepository.findUserByUsername(appUser.getUsername());
        Assertions.assertEquals(appUser.getId(), foundedAppUser.getId());
    }

    @Test
    void findByLastname(){
        AppUser appUser = Generator.generateUser();
        userRepository.save(appUser);
        Assertions.assertNotNull(userRepository.findUsersByLastname(appUser.getLastname()));
    }

    @Test
    void findByFirstname(){
        AppUser appUser = Generator.generateUser();
        userRepository.save(appUser);
        Assertions.assertNotNull(userRepository.findUsersByFirstname(appUser.getFirstname()));
    }

    @Test
    void findByFirstnameAndLastname(){
        AppUser appUser = Generator.generateUser();
        userRepository.save(appUser);
        Assertions.assertNotNull(userRepository.findUsersByFirstnameAndLastname(appUser.getFirstname(), appUser.getLastname()));
    }


}
