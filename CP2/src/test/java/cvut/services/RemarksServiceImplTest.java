package cvut.services;
import cvut.Application;
import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.Admin;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.model.Remarks;
import cvut.repository.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class RemarksServiceImplTest {

    @Autowired
    private RemarksServiceImpl remarksServiceImpl;

    @Autowired
    private CritiqueServiceImpl critiqueServiceImpl;

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

        Admin admin = new Admin("vllwplw", "wfpwpfpqfl", "woqeqefekfpwfkpwf", "wfoijwfif", "efowjfofwfiwjf@gmail.com");
        adminRepository.save(admin);
        Critique critique = critiqueServiceImpl.findById(182L);

        //TODO
        //verify text <5 and >3000
        assertThrows(ValidationException.class, () -> {
            remarksServiceImpl.makeRemarksAndSave(velky_text, critique.getId(), admin.getUsername());
            remarksServiceImpl.makeRemarksAndSave(maly_text, 205L, admin.getUsername());
        });

        //TODO
        //verify not found admin, critique
        assertThrows(NotFoundException.class, () -> {
            remarksServiceImpl.makeRemarksAndSave(norm_text, 20000L, admin.getUsername());
            remarksServiceImpl.makeRemarksAndSave(norm_text, 205L, admin.getUsername());
        });

        //verify save remark
        int count_1;
        int count_2;

        count_1 = remarksServiceImpl.findAll().size();

        remarksServiceImpl.makeRemarksAndSave(norm_text, 205L, admin.getUsername());

        count_2 = remarksServiceImpl.findAll().size();

        assertEquals(count_1 + 1, count_2);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void deleteRemark(){

        String norm_text = "Je to proste super";

        int count_1 = remarksServiceImpl.findAll().size();
        int count_2 = 0;

        //verify not found remark
        assertThrows(NotFoundException.class, () -> {
            remarksServiceImpl.deleteById(100L);
        });

        remarksServiceImpl.deleteById(3L);

        count_2 = remarksServiceImpl.findAll().size();

        assertEquals(count_1, count_2 + 1);

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
        Remarks remarks = remarksServiceImpl.findById(4L);
        //verify not found remark
        assertThrows(NotFoundException.class, () -> {
            remarksServiceImpl.update( norm_text, 52L);
        });

        //verify update na text, ktery <5 and >3000
        assertThrows(ValidationException.class, () -> {
            remarksServiceImpl.update(maly_text, 52L);
            remarksServiceImpl.update(velky_text, 52L);
        });

        //verify update

        String text = "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmaaaaaaasaaaallsamkamkamamamamdkamdkamdkamdkamkdmdkm;mamamdam;dm";

        remarksServiceImpl.update(text, remarks.getId());

        Assertions.assertEquals(text, remarks.getText());
    }


}