package cvut.services;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import cvut.model.Comment;
import cvut.model.Critique;
import cvut.repository.CommentRepository;
import cvut.repository.CritiqueRepository;
import cvut.repository.RatingVoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CommentServiceTest {

//    @Autowired
//    private CommentService commentService;
//    @Autowired
//    private CommentRepository commentRepository;
//
//
//    @Test
//    public void createCommentWhenCritiqueHasInProcessedState(){
//        String text = "My text";
//        AppUser appUser = Generator.generateUser();
//        Critique critique = Generator.generateCritique(); //default in process;
//
//        //verify, that critique does not have comments
//        List<Comment> comments = commentService.findCommentsByCritique_Id(critique.getId());
//        Assertions.assertTrue(comments.isEmpty());
//
//        //create cooment. critique in state in processed, comment has not created
//        commentService.createComment(text, appUser.getId(), critique.getId());
//        List<Comment> comments2 = commentService.findCommentsByCritique_Id(critique.getId());
//        Assertions.assertTrue(comments.isEmpty());
//
//    }
//
//    @Test
//    public void createCommentWhenCritiqueHasNotInProcessedState(){
//
//    }


}
