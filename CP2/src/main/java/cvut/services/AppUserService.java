package cvut.services;

import cvut.model.AppUser;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AppUserService {
    AppUser findById( Long appUserId);
    List<AppUser> findAll();
    AppUser findByUsername(String username);
    AppUser findByEmail( String email);
    void save( AppUser appUser);
    void update(Long appUserId, String username, String email);
    void deleteById(Long appUserId);
}
