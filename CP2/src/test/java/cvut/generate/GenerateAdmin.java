package cvut.generate;

import cvut.Application;
import cvut.config.utils.Generator;
import cvut.model.Admin;
import cvut.model.AppUser;
import cvut.repository.AdminRepository;
import cvut.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackageClasses = Application.class)
public class GenerateAdmin {
    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void generate() {
//        for(int i = 0; i < 100; i++) {
//            Admin admin = Generator.generateAdmin();
//            adminRepository.save(admin);
//        }
    }
}
