package cvut.services;

import cvut.model.AppUser;
import cvut.security.dto.RegistrationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AppUserService extends UserDetailsService {
    AppUser findById(Long appUserId);

    List<AppUser> findAll();

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    void save(RegistrationRequest appUser);

    void save(AppUser appUser);

    void update(Long appUserId, String username, String email);

    void deleteById(Long appUserId);

    void findUsersWithSpecifiedCommentQuantity(int quantity);
}
