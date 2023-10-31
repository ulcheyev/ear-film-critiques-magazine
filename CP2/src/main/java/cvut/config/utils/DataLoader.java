package cvut.config.utils;

import cvut.exception.NotFoundException;
import cvut.model.Admin;
import cvut.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private static final String username = "admin";
    private static final String pass = "admin";

    private final AppUserService service;

    @Override
    public void run(String... args) {
        loadAdmin();
    }

    private void loadAdmin() {
        try {
            service.findByUsername(username);
        } catch (NotFoundException e) {
            Admin admin = new Admin(
                    "admin",
                    "admin",
                    username,
                    pass,
                    "admin@gmail.com"
            );
            service.save(admin);
        }
    }


}
