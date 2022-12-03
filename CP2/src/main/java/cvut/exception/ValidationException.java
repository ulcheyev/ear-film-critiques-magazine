package cvut.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ValidationException extends RuntimeException{

    public ValidationException(String str) {
        super(str);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
