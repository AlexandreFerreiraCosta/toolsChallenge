package br.com.proenix.toolsChallenge.dto.transaction;

import br.com.proenix.toolsChallenge.util.validator.interfaces.IEPaymentTypeValidator;
import jakarta.validation.constraints.NotNull;

public record PaymentMethodCreateDto(
        @IEPaymentTypeValidator String paymentType,

        @NotNull(message = "{not-null}") Integer installments) {
}
