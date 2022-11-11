package cvut.config.exception;

public class ValidationException extends RuntimeException{

    public ValidationException(String str) {
        super(str);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
