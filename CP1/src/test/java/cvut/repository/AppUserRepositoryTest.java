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
    private AppUserRepository appUserRepository;


    @Test
    void addUser(){
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        Assertions.assertNotNull(appUserRepository.findById(appUser.getId()));
    }

    @Test
    void findByUsername(){
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        AppUser foundedAppUser = appUserRepository.findAppUserByUsername(appUser.getUsername()).get();
        Assertions.assertEquals(appUser.getId(), foundedAppUser.getId());
    }

    @Test
    void findByLastname(){
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        Assertions.assertNotNull(appUserRepository.findAppUsersByLastname(appUser.getLastname()));
    }

    @Test
    void findByFirstname(){
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        Assertions.assertNotNull(appUserRepository.findAppUsersByFirstname(appUser.getFirstname()));
    }

    @Test
    void findByFirstnameAndLastname(){
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        Assertions.assertNotNull(appUserRepository.findAppUsersByFirstnameAndLastname(appUser.getFirstname(), appUser.getLastname()));
    }


}
