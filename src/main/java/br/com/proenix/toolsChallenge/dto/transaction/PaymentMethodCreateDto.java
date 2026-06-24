package br.com.proenix.toolsChallenge.dto.transaction;

import br.com.proenix.toolsChallenge.util.validator.interfaces.IEPaymentTypeValidator;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodCreateDto {
    @IEPaymentTypeValidator
    private String paymentType;

    @NotNull(message = "{not-null}")
    private Integer installments;
}
