package cvut.security.validators;

import cvut.exception.ValidationException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNumberValidatorImplementation implements ConstraintValidator<NotNumberValidator, String> {
    @Override
    public void initialize(NotNumberValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.matches("-?\\d+(\\.\\d+)?")) {
            return true;
        }
        throw new ValidationException("The value must be string");
    }
}
