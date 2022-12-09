package cvut.services;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)



public class AppUserServiceImplTest {

    @Autowired
    private AppUserServiceImpl appUserServiceImpl;

    @Test
    public void verifyThatUserDoesNotHaveUniqueUsernameAndEmail(){

        AppUser appUser1 = new AppUser("Natalie", "Portman", "blablahe", "232323fsv", "DomenicoJast@gmail.com");
        AppUser appUser2 = new AppUser("Konstantin", "Moravsky", "tina.runte", "1232ddfs", "kostechka@gmail.com");

        //verify email
        assertThrows(ValidationException.class, () -> {
            appUserServiceImpl.save(appUser1);
        });

        //check that a user with non-unique mail has not been added to the table
        assertThrows(NotFoundException.class, () -> {
            appUserServiceImpl.findByUsername("blablahe");
        });

        //verify username
        assertThrows(ValidationException.class, () -> {
            appUserServiceImpl.save(appUser2);
        });

        //check that a user with non-unique username has not been added to the table
        assertThrows(NotFoundException.class, () -> {
            appUserServiceImpl.findByEmail("kostechka@gmail.com");
        });

    }

    @Test
    public void deleteUserById(){

        AppUser appUser1 = new AppUser("Kasha", "Molochnaya", "kashechka1", "213132dsaddfx", "kashecka_mleko1@gmail.com");
        AppUser appUser2 = new AppUser("Joji", "Jojik", "jjoji6", "qedkqmdkada", "jjjjoji543@gmail.com");

        appUserServiceImpl.save(appUser1);


        assertThrows(NotFoundException.class, () -> {
            appUserServiceImpl.findByUsername(appUser2.getUsername());
        });

        int count1 = appUserServiceImpl.findAll().size();

        appUserServiceImpl.deleteById(appUser1.getId());

        int count2 = appUserServiceImpl.findAll().size();

        assertEquals(count1, count2 + 1);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void updateEmailUser(){

        AppUser appUser = appUserServiceImpl.findById(300L);
        AppUser appUser1 = Generator.generateUser();

        appUserServiceImpl.save(appUser1);

        //verify username
        assertThrows(ValidationException.class, () -> {
            appUserServiceImpl.update(appUser1.getId(), appUser.getUsername(), Generator.generateString());
        });

        //verify email
        assertThrows(ValidationException.class, () -> {
            appUserServiceImpl.update(appUser1.getId(), Generator.generateString(), appUser.getEmail());
        });

        //verify update
        String username = Generator.generateString();
        String mail = Generator.generateString()+"@gmail.com";
        appUserServiceImpl.update(appUser1.getId(), username, mail);


        Assertions.assertEquals(username, appUser1.getUsername());
        Assertions.assertEquals(mail, appUser1.getEmail());

    }

}
