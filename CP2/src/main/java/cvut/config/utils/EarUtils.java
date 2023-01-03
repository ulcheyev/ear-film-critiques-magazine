package cvut.config.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import cvut.model.AppUser;
import cvut.model.Critic;
import cvut.model.CritiqueState;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class EarUtils {

    public static double floorNumber(int scale,double n){
        double sc = Math.pow(10, scale);
        return Math.floor(n*sc)/sc;
    }

    public static HttpHeaders createLocationHeaderFromCurrentUri(String path, Object... uriVariableValues) {
        assert path != null;

        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path(path).buildAndExpand(
                uriVariableValues).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, location.toASCIIString());
        return headers;
    }

    public static class EnumConverter<T extends Enum<T>>
    {

        Class<T> type;

        public EnumConverter(Class<T> type)
        {
            this.type = type;
        }

        public Enum<T> convert(String text)
        {
            for (Enum<T> candidate : type.getEnumConstants()) {
                if (candidate.name().equalsIgnoreCase(text)) {
                    return candidate;
                }
            }

            return null;
        }
    }







}
