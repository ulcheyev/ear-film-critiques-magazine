package cvut.services;
import cvut.Application;
import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.model.Critique;
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
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CritiqueService critiqueService;


    @Test
    public void saveCommentTest(){

        String text_200 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris rutrum, lorem nec fringilla " +
                "convallis, sapien mi maximus dolor, viverra elementum velit sapien et libero. Proin sit amet gravida diam, " +
                "in porttitor sapien. Integer semper urna non rhoncus pretium. Donec vitae eros nunc. Nullam ultricies " +
                "sollicitudin augue, non finibus orci iaculis et. Curabitur nec leo maximus, pretium nisi ut, accumsan dui. " +
                "Donec ornare, mauris lobortis feugiat maximus, lorem felis sodales eros, rutrum sagittis massa sem vitae " +
                "nisl. Nulla eget ligula lobortis, auctor lectus non, posuere nulla. Suspendisse elementum, eros eget " +
                "interdum rhoncus, erat lorem tincidunt nibh, sit amet laoreet nulla tortor non enim. Cras vel sem et neque " +
                "consequat commodo. Pellentesque diam justo, laoreet ut quam at, hendrerit venenatis est. Cras rhoncus " +
                "efficitur est ac lacinia. Nam tincidunt lectus ac sagittis laoreet. Ut at efficitur nisl. Vestibulum " +
                "pulvinar sed risus ac dictum. Praesent in augue nec nisl convallis vestibulum.\n" +
                "\n" +
                "Nam sodales elementum arcu, vel placerat eros sodales sit amet. Vivamus eget risus at neque aliquam maximus. " +
                "Aliquam aliquam eu elit et cursus. Aliquam erat volutpat. Donec eget magna at lacus pharetra sollicitudin. Suspendisse " +
                "ipsum est, rhoncus eu ligula eu, semper tristique nisl. Donec luctus leo arcu. Duis in justo id turpis.";

        AppUser appUser = Generator.generateUser();
        Critique critique = critiqueService.findById(205L);
        Critique critique_neex = Generator.generateCritique(300);
        AppUser appUser_neex = Generator.generateUser();
        appUserService.save(appUser);
        critiqueService.save(critique);


        String normal_text = "Its my first comment";


        //verify long text (>180 words)
        assertThrows(ValidationException.class, () -> {
            commentService.save(text_200, appUser.getId(), critique.getId());
        });

        //verify not found appUser
        assertThrows(NotFoundException.class, () -> {
            commentService.save(normal_text, 2000000000L, critique.getId());
        });

        //verify not found critique
        assertThrows(NotFoundException.class, () -> {
            commentService.save(normal_text, appUser.getId(), 200000000000L);
        });


        //verify save comment
        int count1 = 0;
        int count2 = 0;

        count1 = commentService.getAll().size();

        commentService.save(normal_text, appUser.getId(), critique.getId());

        count2 = commentService.getAll().size();

        assertEquals(count1 + 1, count2);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void deleteCommentTest(){

        AppUser appUser = Generator.generateUser();
        Critique critique = critiqueService.findById(205L);

        appUserService.save(appUser);
        critiqueService.save(critique);


        String normal_text = "Its my first comment";

        //verify delete comment, ktery neexistuje v tabulce
        assertThrows(NotFoundException.class, () -> {
            commentService.deleteComment(52L);
        });

        //verify delete comment
        int count1 = 0;
        int count2 = 0;

        count1 = commentService.getAll().size();

        commentService.deleteComment(10L);

        count2 = commentService.getAll().size();

        assertEquals(count1, count2+1);

    }

    @Test
    public void updateCommentTest(){

        String text_200 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris rutrum, lorem nec fringilla " +
                "convallis, sapien mi maximus dolor, viverra elementum velit sapien et libero. Proin sit amet gravida diam, " +
                "in porttitor sapien. Integer semper urna non rhoncus pretium. Donec vitae eros nunc. Nullam ultricies " +
                "sollicitudin augue, non finibus orci iaculis et. Curabitur nec leo maximus, pretium nisi ut, accumsan dui. " +
                "Donec ornare, mauris lobortis feugiat maximus, lorem felis sodales eros, rutrum sagittis massa sem vitae " +
                "nisl. Nulla eget ligula lobortis, auctor lectus non, posuere nulla. Suspendisse elementum, eros eget " +
                "interdum rhoncus, errjwirjwoijfojqriwjrpqpmdkalkdklnqijf3jrjwjdamd;qpo3rjpiefjpajdadqfat lorem tincidunt nibh, sit amet laoreet nulla tortor non enim. Cras vel sem et neque " +
                "consequat commodo. Pellentesque diam justo, laoreet ut quam at, hendrerit venenatis est. Cras rhoncus " +
                "efficitur est ac lacinia. Nam tincidunt lectus ac sagittis laoreet. Ut at efficitur nisl. Vestibulum " +
                "pulvinar sed risus ac dictum. 2 2 2 2 2 2 3 32 3 2r q r 2r 2lktrlkwtkwm;rqrmq'klr23t2tPraesent in augue nec nisl convallis vestibulum.\n" +
                "\n" +
                "Nam sodales elementum arcu, vel placerat eros sodales sit amet. Vivamus eget risus at neque aliquam maximus. " +
                "Aliquam aliquam eu elit et cursus. Aliquam erat volutpat. Donec eget magna at lacus pharetra sollicitudin. Suspendisse " +
                "ipsum est, rhoncus eu ligula eu, semper tristique nisl. Donec luctus leo arcu. Duis in justo id turpis.";


        AppUser appUser = Generator.generateUser();
        Critique critique = critiqueService.findById(205L);
        appUserService.save(appUser);
        critiqueService.save(critique);

        String normal_text = "Its my first comment";

        //verify update comment, ktery neexistuje v tabulce
        assertThrows(NotFoundException.class, () -> {
            commentService.updateComment( 52L, normal_text);
        });

        //verify update comment

        String text = Generator.generateString();

        commentService.updateComment(12L, text);

        Assertions.assertEquals(text, commentService.findById(12L).getText());
    }
}
