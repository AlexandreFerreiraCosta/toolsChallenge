package br.com.proenix.toolsChallenge.dto.transaction;

import br.com.proenix.toolsChallenge.enums.ETransactionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DescriptionDto(
        BigDecimal value,
        LocalDateTime date,
        String establishment,
        String nsu,
        String authorizationCode,
        @Enumerated(EnumType.STRING) ETransactionStatus transactionStatus) {
}
