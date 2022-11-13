package cvut.services;
import cvut.Application;
import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
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
public class RemarksServiceTest {

    @Autowired
    private RemarksService remarksService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CritiqueService critiqueService;

    @Test
    public void makeAndSaveRemark(){

        String velky_text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu cursus massa, sit amet " +
                "sagittis sem. Vivamus eget vehicula massa, at tristique turpis. Aliquam eros ligula, sagittis ut interdum " +
                "vitae, pellentesque id ligula. Suspendisse et scelerisque sem, a consequat orci. Nulla facilisi. Cras " +
                "dapibus, turpis a facilisis varius, purus nisl elementum purus, eget lobortis quam lacus vitae elit. Fusce " +
                "purus nunc, egestas vel enim ac, mollis volutpat ligula. Vestibulum ante ipsum primis in faucibus orci " +
                "luctus et ultrices posuere cubilia curae; Aenean faucibus posuere sapien, ut ultricies neque placerat et. " +
                "Aliquam tempor ornare ipsum, in ultrices ex ultricies gravida. Duis pulvinar ultrices efficitur. Aenean " +
                "euismod, turpis et vehicula viverra, lacus ante interdum purus, vitae tincidunt ex arcu ac lectus. Sed nec " +
                "eleifend justo. Vivamus gravida ex erat, et fringilla lorem luctus sit amet. Fusce ac laoreet massa. Vestibulum " +
                "vitae sodales nisl.\n" +
                "\n" +
                "Nunc eu elementum mi. Cras vulputate pretium ultrices. Pellentesque metus est, malesuada eu arcu vitae, " +
                "facilisis euismod lectus. Donec molestie nec magna sit amet consectetur. Nam auctor, lectus eu rutrum commodo, " +
                "libero ante pharetra tellus, ut tincidunt odio ipsum sit amet tortor. Sed urna ante, mattis ac ipsum sed, ultrices " +
                "placerat elit. Nullam quis semper odio.";

        String maly_text = "Norm";

        String norm_text = "Je to proste super";

        //verify text <5 and >3000
        assertThrows(ValidationException.class, () -> {
            remarksService.makeRemarksAndSave(velky_text, 205L, 481L);
            remarksService.makeRemarksAndSave(maly_text, 205L, 481L);
        });

        //verify not found admin, critique
        assertThrows(NotFoundException.class, () -> {
            remarksService.makeRemarksAndSave(norm_text, 20000L, 481L);
            remarksService.makeRemarksAndSave(norm_text, 205L, 40000L);
        });

        //verify save remark

        int count_1 = 0;
        int count_2 = 0;

        count_1 = remarksService.getAll().size();

        remarksService.makeRemarksAndSave(norm_text, 205L, 481L);

        count_2 = remarksService.getAll().size();

        assertEquals(count_1 + 1, count_2);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void deleteRemark(){

        String norm_text = "Je to proste super";

        int count_1 = remarksService.getAll().size();
        int count_2 = 0;

        //verify not found remark
        assertThrows(NotFoundException.class, () -> {
            remarksService.deleteById(100L);
        });

        remarksService.deleteById(3L);

        count_2 = remarksService.getAll().size();

        assertEquals(count_1, count_2 + 1);

    }

    @Test
    public void updateRemark(){

        String velky_text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu cursus massa, sit amet " +
                "sagittis sem. Vivamus eget vehicula massa, at tristique turpis. Aliquam eros ligula, sagittis ut interdum " +
                "vitae, pellentesque id ligula. Suspendisse et scelerisque sem, a consequat orci. Nulla facilisi. Cras " +
                "dapibus, turpis a facilisis varius, purus nisl elementum purus, eget lobortis quam lacus vitae elit. Fusce " +
                "purus nunc, egestas vel enim ac, mollis volutpat ligula. Vestibulum ante ipsum primis in faucibus orci " +
                "luctus et ultrices posuere cubilia curae; Aenean faucibus posuere sapien, ut ultricies neque placerat et. " +
                "Aliquam tempor ornare ipsum, in ultrices ex ultricies gravida. Duis pulvinar ultrices efficitur. Aenean " +
                "euismod, turpis et vehicula viverra, lacus ante interdum purus, vitae tincidunt ex arcu ac lectus. Sed nec " +
                "eleifend justo. Vivamus gravida ex erat, et fringilla lorem luctus sit amet. Fusce ac laoreet massa. Vestibulum " +
                "vitae sodales nisl.\n" +
                "\n" +
                "Nunc eu elementum mi. Cras vulputate pretium ultrices. Pellentesque metus est, malesuada eu arcu vitae, " +
                "facilisis euismod lectus. Donec molestie nec magna sit amet consectetur. Nam auctor, lectus eu rutrum commodo, " +
                "libero ante pharetra tellus, ut tincidunt odio ipsum sit amet tortor. Sed urna ante, mattis ac ipsum sed, ultrices " +
                "placerat elit. Nullam quis semper odio.";

        String maly_text = "Norm";

        String norm_text = "Je to proste super puper vuper";

        //verify not found remark
        assertThrows(NotFoundException.class, () -> {
            remarksService.update( norm_text, 52L);
        });

        //verify update na text, ktery <5 and >3000
        assertThrows(ValidationException.class, () -> {
            remarksService.update(maly_text, 52L);
            remarksService.update(velky_text, 52L);
        });

        //verify update

        String text = Generator.generateString();

        remarksService.update(text, 1L);

        Assertions.assertEquals(text, remarksService.findById(1L).getText());
    }


}