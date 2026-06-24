package br.com.proenix.toolsChallenge.util.validator.interfaces;

import br.com.proenix.toolsChallenge.util.validator.EPaymentTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EPaymentTypeValidator.class)
@Documented
public @interface IEPaymentTypeValidator {
    String message() default "{payment-type-invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
