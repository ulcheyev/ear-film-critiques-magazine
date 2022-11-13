package cvut.services;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public void save(@NonNull AppUser appUser) {
        Optional<AppUser> appUserByUsername = appUserRepository
                .findAppUserByUsername(appUser.getUsername());
        if (appUserByUsername.isPresent()) {
            throw new ValidationException("Username " + appUser.getUsername() + " has been taken");
        }
        Optional<AppUser> appUserByEmail = appUserRepository
                .findAppUserByEmail(appUser.getEmail());
        if (appUserByEmail.isPresent()) {
            throw new ValidationException("Email " + appUser.getEmail() + " has been taken");
        }
        appUserRepository.save(appUser);
    }

    public AppUser findById(@NonNull Long appUserId) {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new NotFoundException("User with id " + appUserId + " does not exist"));
        return appUser;
    }

    public AppUser findByUsername(@NonNull String username) {
        AppUser appUser = appUserRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username " + username + " does not exist"));
        return appUser;
    }

    public AppUser findByEmail(@NonNull String email) {
        AppUser appUser = appUserRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));
        return appUser;
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public void deleteById(@NonNull Long appUserId) {
        boolean exists = appUserRepository.existsById(appUserId);
        if (!exists) {
            throw new NotFoundException("Can not delete user with id " + appUserId + ". User does not exist");
        }
        appUserRepository.deleteById(appUserId);
    }

    @Transactional
    public void update(@NonNull Long appUserId, String username, String email) {
        AppUser appUser = findById(appUserId);

        if (username != null && username.length() > 0 && !appUser.getUsername().equals(username)) {
            Optional<AppUser> appUserByUsername = appUserRepository
                    .findAppUserByUsername(username);
            if (appUserByUsername.isPresent()) {
                throw new ValidationException("Username " + username + " has been taken");
            }
            appUser.setUsername(username);
        }
        if (email != null && email.length() > 0 && !appUser.getEmail().equals(email)) {
            Optional<AppUser> appUserByEmail = appUserRepository
                    .findAppUserByEmail(email);
            if (appUserByEmail.isPresent()) {
                throw new ValidationException("Email " + email + " has been taken");
            }
                appUser.setEmail(email);
            }
        }

}

