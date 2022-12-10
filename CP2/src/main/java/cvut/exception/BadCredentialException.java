package cvut.exception;

public class BadCredentialException extends RuntimeException{

    public BadCredentialException(String str) {
        super(str);
    }

    public BadCredentialException(String message, Throwable cause) {
        super(message, cause);
    }
}
