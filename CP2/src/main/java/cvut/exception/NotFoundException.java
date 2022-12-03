package cvut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String str) {
        super(str);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}




