package cvut.services;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.*;
import cvut.repository.CriticRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @Autowired
    private AppUserServiceImpl appUserServiceImpl;

    @Autowired
    private CritiqueServiceImpl critiqueServiceImpl;

    @Autowired
    private CriticRepository criticRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MainRoleService mainRoleService;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveCommentTest() {

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
        AppUser appUser1 = Generator.generateUser();
        Critic critic = new Critic("Lola", "Lolova", "lolosfaflo", "flfplfpafd", "mdqefqffoq@gmail.com");
        criticRepository.save(critic);
        Critique critique = Generator.generateCritique(CritiqueState.ACCEPTED, 305);
        appUserServiceImpl.save(appUser);
        MainRole mainRole = Generator.generateMainRole();
        mainRoleService.save(mainRole);
        Film film = Generator.generateFilm();
        film.setMainRoleList(List.of(mainRole));
        filmService.save(film);
        critique.setFilm(film);
        appUserService.save(critique.getCritiqueOwner());
        critiqueServiceImpl.save(critique);


        String normal_text = "Its my first comment";


        //verify long text (>180 words)
        assertThrows(ValidationException.class, () -> {
            commentServiceImpl.save(text_200, appUser.getUsername(), critique.getId());
        });

        //verify not found appUser
        assertThrows(NotFoundException.class, () -> {
            commentServiceImpl.save(normal_text, appUser1.getUsername(), critique.getId());
        });

        //checking that when a critic is in the "IN_PROCESSED" state, only the critic can comment on it

        int count_before_save_comment = commentServiceImpl.findAll().size();

        commentServiceImpl.save(normal_text, appUser.getUsername(), 6L);

        int count_after_save = commentServiceImpl.findAll().size();

        assertEquals(count_before_save_comment + 1, count_after_save);

        //verify not found critique
        assertThrows(NotFoundException.class, () -> {
            commentServiceImpl.save(normal_text, critic.getUsername(), 200000000000L);
        });


        //verify save comment
        int count1 = 0;
        int count2 = 0;

        count1 = commentServiceImpl.findAll().size();

        commentServiceImpl.save(normal_text, appUser.getUsername(), critique.getId());

        count2 = commentServiceImpl.findAll().size();

        assertEquals(count1 + 1, count2);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void deleteCommentTest() {

        AppUser appUser = Generator.generateUser();
        Critique critique = critiqueServiceImpl.findById(205L);

        appUserServiceImpl.save(appUser);
        critiqueServiceImpl.save(critique);


        //verify delete comment, ktery neexistuje v tabulce
        assertThrows(NotFoundException.class, () -> {
            commentServiceImpl.deleteComment(52L);
        });

        //verify delete comment
        int count1 = 0;
        int count2 = 0;

        count1 = commentServiceImpl.findAll().size();

        commentServiceImpl.deleteComment(10L);

        count2 = commentServiceImpl.findAll().size();

        assertEquals(count1, count2 + 1);

    }

    @Test
    public void updateCommentTest() {

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
        Critique critique = critiqueServiceImpl.findById(205L);
        appUserServiceImpl.save(appUser);
        critiqueServiceImpl.save(critique);

        String normal_text = "Its my first comment";

        //verify update comment, ktery neexistuje v tabulce
        assertThrows(NotFoundException.class, () -> {
            commentServiceImpl.update(52L, normal_text);
        });

        //verify update comment

        String text = Generator.generateString();

        commentServiceImpl.update(12L, text);

        Assertions.assertEquals(text, commentServiceImpl.findById(12L).getText());

    }
}

