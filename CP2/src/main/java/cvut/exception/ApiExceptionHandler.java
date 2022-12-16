package cvut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        NotFoundExceptionPayload foo = new NotFoundExceptionPayload(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(foo, notFound);
    }

    @ExceptionHandler(value = {BadCredentialException.class})
    public ResponseEntity<Object> handleBadCredential(BadCredentialException e){
        HttpStatus bad = HttpStatus.BAD_REQUEST;
        BadCredentialExceptionPayload foo = new BadCredentialExceptionPayload(
                e.getMessage(),
                bad,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(foo, bad);
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ValidationExceptionPayload foo = new ValidationExceptionPayload(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(foo, badRequest);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        BadRequestExceptionPayload foo = new BadRequestExceptionPayload(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(foo, badRequest);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        HttpStatus accessDenied = HttpStatus.PRECONDITION_FAILED;
        ValidationExceptionPayload foo = new ValidationExceptionPayload(
               "Access is denied",
                accessDenied,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(foo, accessDenied);
    }


}
