package cvut.controllers;


import cvut.config.utils.EarUtils;
import cvut.model.dto.AppUserInfoUpdateDTO;
import cvut.security.dto.AuthenticationRequest;
import cvut.security.dto.AuthenticationResponse;
import cvut.security.dto.RegistrationRequest;
import cvut.security.validators.NotStringValidator;
import cvut.services.AppUserService;
import cvut.services.AuthenticationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final AuthenticationService authenticationService;


    @PostMapping(value = "api/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @NonNull AuthenticationRequest request) {
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/api/critiques");
        return ResponseEntity.ok().headers(headers).body(authenticationService.authenticate(request));
    }

    @PostMapping(value = "api/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @NonNull RegistrationRequest request) {
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/api/login");
        return ResponseEntity.ok().headers(headers).body(authenticationService.register(request));
    }


    @DeleteMapping(value = "api/system/users/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserById(@NotStringValidator @NonNull @PathVariable("userId") String userId) {
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/api/critiques");

        appUserService.deleteById(Long.parseLong(userId));
        return ResponseEntity.ok().headers(headers).body("User Successfully deleted");
    }

    @PutMapping(value = "api/profile/{userName}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER') and @appUserServiceImpl.findByUsername(#userName).username.equals(principal.username)" +
            "or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateUser(@NonNull @PathVariable("userName") String userName,
                                             @RequestBody @NonNull AppUserInfoUpdateDTO request) {
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/api/critiques");
        appUserService.update(appUserService.findByUsername(userName).getId(), request.getUsername(), request.getEmail());
        return ResponseEntity.ok().headers(headers).body("User Successfully deleted");
    }


}
