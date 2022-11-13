package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;


    @Test
    void addUser(){
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        Optional<AppUser> byId = appUserRepository.findById(appUser.getId());

        //Assert
        Assertions.assertTrue(byId.isPresent());
    }

    @Test
    void findByUsername(){
        String username = "joey.reynolds";
        Optional<AppUser> appUserByUsername = appUserRepository.findAppUserByUsername(username);
        //Assert
        Assertions.assertTrue(appUserByUsername.isPresent());
        Assertions.assertEquals(username, appUserByUsername.get().getUsername());
    }


    @Test
    void findByFirstnameAndLastnameGeneratedUser(){
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        List<AppUser> appUsersByFirstnameAndLastname = appUserRepository
                .findAppUsersByFirstnameAndLastname(appUser.getFirstname(), appUser.getLastname());

        //Assert
        Assertions.assertFalse(appUsersByFirstnameAndLastname.isEmpty());

        //Assert
        assertThat(appUsersByFirstnameAndLastname)
                .extracting(AppUser::getFirstname, AppUser::getLastname)
                .containsExactlyInAnyOrder(
                        tuple(appUser.getFirstname(), appUser.getLastname())
                );
    }


}
