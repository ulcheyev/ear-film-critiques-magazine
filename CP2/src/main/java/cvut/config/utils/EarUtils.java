package cvut.config.utils;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import cvut.exception.ValidationException;
import cvut.model.AppUser;
import cvut.model.Critic;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EarUtils {

    public static double floorNumber(int scale,double n){
        double sc = Math.pow(10, scale);
        return Math.floor(n*sc)/sc;
    }

    public static Critic toCritic(AppUser appUser){
        return new Critic(appUser.getFirstname(),
                appUser.getLastname(),
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.toString());
    }

    public static HttpHeaders createLocationHeaderFromCurrentUri(String path, Object... uriVariableValues) {
        assert path != null;

        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path(path).buildAndExpand(
                uriVariableValues).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, location.toASCIIString());
        return headers;
    }


}
