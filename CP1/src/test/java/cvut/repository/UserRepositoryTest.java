package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void addUser(){
        User user = Generator.generateUser();
        userRepository.save(user);
        Assertions.assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    void findByUsername(){
        User user = Generator.generateUser();
        userRepository.save(user);
        User foundedUser = userRepository.findUserByUsername(user.getUsername());
        Assertions.assertEquals(user.getId(), foundedUser.getId());
    }

    @Test
    void findByLastname(){
        User user = Generator.generateUser();
        userRepository.save(user);
        Assertions.assertNotNull(userRepository.findUsersByLastname(user.getLastname()));
    }

    @Test
    void findByFirstname(){
        User user = Generator.generateUser();
        userRepository.save(user);
        Assertions.assertNotNull(userRepository.findUsersByFirstname(user.getFirstname()));
    }

    @Test
    void findByFirstnameAndLastname(){
        User user = Generator.generateUser();
        userRepository.save(user);
        Assertions.assertNotNull(userRepository.findUsersByFirstnameAndLastname(user.getFirstname(), user.getLastname()));
    }


}
