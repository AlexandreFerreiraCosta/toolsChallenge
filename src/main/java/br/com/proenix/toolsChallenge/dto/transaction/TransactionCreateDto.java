package br.com.proenix.toolsChallenge.dto.transaction;

import br.com.proenix.toolsChallenge.util.deserializer.RemoveCharacterDeserializer;
import br.com.proenix.toolsChallenge.util.deserializer.RemoveSpecialCharacterDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import tools.jackson.databind.annotation.JsonDeserialize;

@Getter
@Setter
public class TransactionCreateDto {
    @JsonDeserialize(using = RemoveSpecialCharacterDeserializer.class)
    @NotBlank(message = "{not-blank}")
    @Length(max = 20)
    private String transactionId;

    @JsonDeserialize(using = RemoveCharacterDeserializer.class)
    @NotBlank(message = "{not-blank}")
    @Length(max = 16)
    private String card;

    @NotNull(message = "{not-null}")
    @Valid
    private DescriptionCreateDto description;

    @NotNull(message = "{not-null}")
    @Valid
    private PaymentMethodCreateDto paymentMethod;
}
