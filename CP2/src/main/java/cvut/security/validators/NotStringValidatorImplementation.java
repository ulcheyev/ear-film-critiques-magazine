package cvut.security.validators;

import cvut.exception.ValidationException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotStringValidatorImplementation implements ConstraintValidator<NotStringValidator, String> {


    @Override
    public void initialize(NotStringValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.matches("^-?\\d+$")){
            return true;
        }
        throw new ValidationException("The value must be digit");
    }
}
