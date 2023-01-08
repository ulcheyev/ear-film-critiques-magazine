package cvut.repository;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class AppUserRepositoryTest {

    private final AppUserRepository appUserRepository;
    private final CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public AppUserRepositoryTest(AppUserRepository appUserRepository, CommentRepository commentRepository) {
        this.appUserRepository = appUserRepository;
        this.commentRepository = commentRepository;
    }


    @Test
    void addUser() {
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        Optional<AppUser> byId = appUserRepository.findById(appUser.getId());

        //Assert
        assertTrue(byId.isPresent());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void findByUsername() {
        AppUser appUser = Generator.generateUser();
        appUserRepository.save(appUser);
        String username = appUser.getUsername();
        Optional<AppUser> appUserByUsername = appUserRepository.findAppUserByUsername(username);
        //Assert
        assertTrue(appUserByUsername.isPresent());
        assertEquals(username, appUserByUsername.get().getUsername());
    }


    @Test
    void findByFirstnameAndLastnameGeneratedUser() {
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

    @Test
    void testNamedQueryFindUsersWithCSpecifiedCommentQuantity() {
        // Set data

        Query query = em.createNamedQuery("AppUser.findUsersWithSpecifiedCommentQuantity");

        // Set query parameters
        query.setParameter(1, 1);

        // Execute the query and get the results
        List<AppUser> appUsers = query.getResultList();

        assertNotNull(appUsers);
        Assertions.assertFalse(appUsers.isEmpty());
        Assertions.assertNotEquals(appUsers.size(), 0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void testFindUsersByLastname() {
        // Arrange
        String lastname = "Smith";

        Query query = em.createNamedQuery("findUsersByLastnameNNQ");
        query.setParameter("lastname", lastname);
        List<AppUser> users = query.getResultList();

        // Assert
        assertNotNull(users.size());
        for (AppUser user : users) {
            assertEquals(lastname, user.getLastname());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    void testFindUsersWithSpecifiedCommentQuantity() {
        // Arrange
        int minCommentQuantity = 0;
        Date date = new Date();
        AppUser appUser = Generator.generateUser();
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 10);
        Comment comment = new Comment("hahaha", date, appUser, critique);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        appUser.setCommentList(commentList);

        Query query = em.createNamedQuery("AppUser.findUsersWithSpecifiedCommentQuantityNNQ");
        query.setParameter(1, minCommentQuantity);
        List<AppUser> users = query.getResultList();
        List<Comment> comments;
//        // Assert
        assertNotNull(users.size());
        for (int i = 0; i < users.size(); i++) {
            comments = commentRepository.findAllByAppUser_Id(users.get(i).getId());
            assertTrue(comments.size() > minCommentQuantity);
        }
        Assertions.assertTrue(users.size() > 1);

    }

}
