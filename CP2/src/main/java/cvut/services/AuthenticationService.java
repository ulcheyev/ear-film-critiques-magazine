package cvut.services;

import cvut.exception.BadCredentialException;
import cvut.security.CustomUserDetail;
import cvut.security.JwtUtils;
import cvut.security.SecurityUtils;
import cvut.security.dto.AuthenticationRequest;
import cvut.security.dto.AuthenticationResponse;
import cvut.security.dto.RegistrationRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(@RequestBody @NonNull RegistrationRequest request) {
        service.save(request);
        final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
        var jwtToken = jwtUtils.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(@RequestBody @NonNull AuthenticationRequest request){

        final CustomUserDetail userDetails = (CustomUserDetail) service.loadUserByUsername(request.getUsername());

        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
            throw new BadCredentialException("Invalid password or username");
        }

        Authentication authentication = authenticationManager
                .authenticate(SecurityUtils.generateUserPassToken(request, userDetails));

        SecurityUtils.setCurrentUser(authentication);
        var jwtToken = jwtUtils.generateToken(userDetails);

        userDetails.erasePass();

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
