package cvut.controllers;


import cvut.config.utils.EarUtils;
import cvut.exception.BadCredentialException;
import cvut.model.dto.AppUserInfoUpdateRequest;
import cvut.model.dto.AuthenticationRequest;
import cvut.model.dto.RegistrationRequest;
import cvut.security.JwtUtils;
import cvut.security.validators.NotStringValidator;
import cvut.services.AppUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping(value = "api/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody @NonNull AuthenticationRequest request){
        final UserDetails userDetails = appUserService.loadUserByUsername(request.getUsername());

        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
            throw new BadCredentialException("Invalid password or username");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/api/critiques");
        return ResponseEntity.ok().headers(headers).body(jwtUtils.generateToken(userDetails));
    }

    @PostMapping(value = "api/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody @NonNull RegistrationRequest request) {
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/api/login");

        appUserService.save(request);
        return ResponseEntity.ok().headers(headers).body("Successfully register");
    }

    @DeleteMapping(value = "api/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserById(@NotStringValidator @NonNull @PathVariable("userId") String userId) {
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/api/critiques");

        appUserService.deleteById(Long.parseLong(userId));
        return ResponseEntity.ok().headers(headers).body("User Successfully deleted");
    }

    @PutMapping(value = "api/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER') and @appUserServiceImpl.findById(#userId).username.equals(principal.username)")
    public ResponseEntity<String> updateUser(@NotStringValidator @NonNull @PathVariable("userId") String userId,
                                             @RequestBody @NonNull AppUserInfoUpdateRequest request) {
        final HttpHeaders headers = EarUtils.createLocationHeaderFromCurrentUri("/api/critiques");
        appUserService.update(Long.parseLong(userId), request.getUsername(), request.getEmail());
        return ResponseEntity.ok().headers(headers).body("User Successfully deleted");
    }






}
