package cvut.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String str) {
        super(str);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
