package cvut.generate;


import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.AppUser;
import cvut.model.Comment;
import cvut.repository.AppUserRepository;
import cvut.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class GenerateComment {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void generate() {
        for (int i = 0; i < 100; i++) {
            Comment comment = Generator.generateComment();
            commentRepository.save(comment);
        }
    }
}
