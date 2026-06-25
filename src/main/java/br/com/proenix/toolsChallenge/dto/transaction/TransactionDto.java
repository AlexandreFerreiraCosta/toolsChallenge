package br.com.proenix.toolsChallenge.dto.transaction;

import java.util.UUID;

public record TransactionDto(
        UUID id,
        String transactionId,
        String card,
        DescriptionDto description,
        PaymentMethodDto paymentMethod) {
}
