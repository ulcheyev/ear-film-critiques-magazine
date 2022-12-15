package cvut.controllers;

import cvut.exception.BadCredentialException;
import cvut.security.AuthenticationRequest;
import cvut.security.JwtUtils;
import cvut.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
            throw new BadCredentialException("Invalid password or username");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        return ResponseEntity.ok(jwtUtils.generateToken(userDetails));

    }



}
