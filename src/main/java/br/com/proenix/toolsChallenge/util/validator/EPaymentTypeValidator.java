package br.com.proenix.toolsChallenge.util.validator;

import br.com.proenix.toolsChallenge.enums.EPaymentType;
import br.com.proenix.toolsChallenge.util.validator.interfaces.IEPaymentTypeValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EPaymentTypeValidator implements ConstraintValidator<IEPaymentTypeValidator, String> {
    @Override
    public boolean isValid(String value,ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }

        return EPaymentType.getByName(value).isPresent();
    }
}
