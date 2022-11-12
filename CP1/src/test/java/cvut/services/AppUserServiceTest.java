package cvut.services;

import cvut.Application;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class AppUserServiceTest {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserService appUserService;

    @Test
    public void verifyThatUserDoesNotHaveUniqueUsernameAndEmail(){

        AppUser appUser1 = new AppUser("Natalie", "Portman", "blablahe", "232323fsv", "DomenicoJast@gmail.com");
        AppUser appUser2 = new AppUser("Konstantin", "Moravsky", "tina.runte", "1232ddfs", "kostechka@gmail.com");

        //verify email
        assertThrows(ValidationException.class, () -> {
            appUserService.save(appUser1);
        });

        //check that a user with non-unique mail has not been added to the table
        assertThrows(NotFoundException.class, () -> {
            appUserService.findById(appUser1.getId());
        });

        //verify username
        assertThrows(ValidationException.class, () -> {
            appUserService.save(appUser2);
        });

        //check that a user with non-unique username has not been added to the table
        assertThrows(NotFoundException.class, () -> {
            appUserService.findById(appUser2.getId());
        });

    }

    @Test
    public void deleteUserById(){

        AppUser appUser1 = new AppUser("Kasha", "Molochnaya", "kashechka", "213132dsaddfx", "kashecka_mleko@gmail.com");
        AppUser appUser2 = new AppUser("Joji", "Jojik", "jjoji6", "qedkqmdkada", "jjjjoji543@gmail.com");

        appUserService.save(appUser1);

        //nefungue, protoze id = null
//        assertThrows(NotFoundException.class, () -> {
//            appUserService.findById(appUser2.getId());
//        });

        int count1 = appUserService.getAll().size();

        appUserService.deleteById(appUser1.getId());

        int count2 = appUserService.getAll().size();

        assertEquals(count1, count2 + 1);

    }

    @Test
    public void updateEmailUser(){
        AppUser appUser1 = new AppUser("Goga", "Gogov", "gggoga902", "wefkwffw4423", "goga902@gmail.com");
        AppUser appUser2 = new AppUser("Giga", "Gigav", "gggoga1002", "wefdswqfw4423", "giga1002@gmail.com");

        appUserService.save(appUser1);
        appUserService.save(appUser2);

        //verify username
        assertThrows(ValidationException.class, () -> {
            appUserService.update(appUser1.getId(), "tina.runte", "goga902@gmail.com");
        });

        //verify email
        assertThrows(ValidationException.class, () -> {
            appUserService.update(appUser1.getId(), "gggoga902", "DomenicoJast@gmail.com");
        });

        //verify update
        appUserService.update(appUser2.getId(), "gogol1", "gogol24@gmail.com");

        String checkNewUsername = "gogol1";
        String checkNewEmail = "gogol24@gmail.com";

        Assertions.assertEquals(checkNewUsername, appUser2.getUsername());
        Assertions.assertEquals(checkNewEmail, appUser2.getEmail());

        appUserService.deleteById(appUser1.getId());
        appUserService.deleteById(appUser2.getId());
    }

}
