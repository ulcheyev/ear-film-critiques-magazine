package cvut.repository;

import cvut.Application;
import cvut.model.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    void testNamedQueryFindAdminWithSpecifiedCritiqueQuantity(){
        List<Admin> admins = adminRepository.findAdminWithSpecifiedCritiqueQuantity(1);
        Assertions.assertNotNull(admins);
        Assertions.assertFalse(admins.isEmpty());
        Assertions.assertEquals(admins.size(), 2);
    }

}
