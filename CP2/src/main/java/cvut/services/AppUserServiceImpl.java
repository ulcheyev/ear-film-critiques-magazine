package cvut.services;
import cvut.config.utils.Mapper;
import cvut.model.Admin;
import cvut.security.CustomUserDetail;
import cvut.security.dto.RegistrationRequest;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.model.Critic;
import cvut.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService{

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;


    public void save(@NonNull RegistrationRequest registrationRequest) {

        if(registrationRequest.getRole() != null &&
            registrationRequest.getRole().equals(Admin.DISCRIMINATOR_VALUE))
        {
            throw new ValidationException("You do not have permission");
        }

        if(!registrationRequest.fieldsAreNotEmpty()){
            throw new ValidationException("You have to fill all fields");
        }
        Optional<AppUser> appUserByUsername = appUserRepository
                .findAppUserByUsername(registrationRequest.getUsername());
        if (appUserByUsername.isPresent()) {
            throw new ValidationException("Username " + registrationRequest.getUsername() + " has been taken");
        }
        Optional<AppUser> appUserByEmail = appUserRepository
                .findAppUserByEmail(registrationRequest.getEmail());
        if (appUserByEmail.isPresent()) {
            throw new ValidationException("Email " + registrationRequest.getEmail() + " has been taken");
        }

        AppUser appUser = new AppUser(
                registrationRequest.getFirstname(),
                registrationRequest.getLastname(),
                registrationRequest.getUsername(),
                registrationRequest.getPassword(),
                registrationRequest.getEmail()
        );

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        String role = registrationRequest.getRole();
        if (role != null) {
            if (registrationRequest.getRole().equals(Critic.DISCRIMINATOR_VALUE)) {
                appUserRepository.save(mapper.toCritic(appUser));
            } else {
                appUserRepository.save(appUser);
            }
        }
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
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
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

    public List<AppUser> findAll() {
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

    public void findUsersWithSpecifiedCommentQuantity(int quantity){
        List<AppUser> appUsers = appUserRepository.findAll();
        List<AppUser> appUserListWithSpecifiedCommentQuantity = null;
        for (int i = 0; i < appUsers.size(); i++){
            if (appUsers.get(i).getCommentList().size() == quantity){
                appUserListWithSpecifiedCommentQuantity.add(appUsers.get(i));
            }
        }
    }

    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = findByUsername(username);
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorityCollection = new ArrayList<>();
        simpleGrantedAuthorityCollection.add(new SimpleGrantedAuthority(appUser.getRole()));
        CustomUserDetail detail = new CustomUserDetail();
        detail.setUser(new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), simpleGrantedAuthorityCollection));
        return detail;
    }

    public boolean checkUserExisting(String username){
        return appUserRepository.findAppUserByUsername(username).isPresent();
    }


}

