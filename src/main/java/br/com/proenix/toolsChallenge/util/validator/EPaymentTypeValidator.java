package br.com.proenix.toolsChallenge.util.validator;

import br.com.proenix.toolsChallenge.enums.EPaymentType;
import br.com.proenix.toolsChallenge.util.validator.interfaces.IEPaymentTypeValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EPaymentTypeValidator implements ConstraintValidator<IEPaymentTypeValidator, String> {
    private static final String REGEX_REMOVE_SPECIAL_CHARACTER = "[^a-zA-Z0-9]";

    @Override
    public boolean isValid(String value,ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }

        String valueReplace = value.replaceAll(REGEX_REMOVE_SPECIAL_CHARACTER,"");

        return EPaymentType.getByName(valueReplace).isPresent();
    }
}
