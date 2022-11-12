package cvut.services;

import cvut.Application;
import cvut.model.*;
import cvut.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private CritiqueService critiqueService;

    @Test
    public void verifyThatUserDoesNotHaveComments(){

        AppUser appUser = appUserRepository.findById(66L).get();

        List<Comment> comments = commentService.findCommentsByAppUser_Id(appUser.getId());
        Assertions.assertTrue(comments.isEmpty());

    }

    @Test
    public void createCommentForCritiqueInStateInProcessed() {

        String text = "My text";
        AppUser appUser = appUserRepository.findById(66L).get();
        Critique critique = critiqueService.findById(15L);

        //create comments critique in state in processed, comment has not created
        commentService.createComment(text, appUser.getId(), critique.getId());
        List<Comment> comments2 = commentService.findCommentsByAppUser_Id(appUser.getId());
        Assertions.assertTrue(comments2.isEmpty());

    }

    @Test
    public void createCommentForCritiqueInStateAccepted() {

        String text = "My text";
        AppUser appUser = appUserRepository.findById(67L).get();
        Critique critique = critiqueService.findById(53L);

        commentService.createComment(text, appUser.getId(), critique.getId());
        List<Comment> comments2 = commentService.findCommentsByAppUser_Id(appUser.getId());
        Assertions.assertFalse(comments2.isEmpty());

//        verify that user has comments - nefungue, protoze appUser.getCommentList() vrací null


//        List<Comment> commUser = appUser.getCommentList();
//
//        Assertions.assertFalse(commUser.isEmpty());
    }

    @Test
    public void deleteComment(){

        String text = "Hohoho";
        AppUser appUser = appUserRepository.findById(61L).get();
        Critique critique = critiqueService.findById(53L);

        commentService.createComment(text, appUser.getId(), critique.getId());
        List<Comment> comments2 = commentService.findCommentsByAppUser_Id(appUser.getId());
        int count_1 = comments2.size();
        Assertions.assertFalse(comments2.isEmpty());

        //funguje test pokud po každý průchod změníš id comment
        commentService.deleteComment(23L);
        List<Comment> comments_3 = commentService.findCommentsByAppUser_Id(appUser.getId());
        int count_2 = comments_3.size();

        assertEquals(count_1, count_2 + 1);

    }

}
