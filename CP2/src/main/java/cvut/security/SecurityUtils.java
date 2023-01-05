package cvut.security;

import cvut.security.dto.AuthenticationRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static void setCurrentUser(Authentication authentication) {
        if(contextIsNull()) {
            final SecurityContext context = new SecurityContextImpl();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
    }

    public static UsernamePasswordAuthenticationToken generateUserPassToken(AuthenticationRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword());
        authenticationToken.setDetails(userDetails);
        return authenticationToken;
    }


    public static boolean contextIsNull() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }



}
