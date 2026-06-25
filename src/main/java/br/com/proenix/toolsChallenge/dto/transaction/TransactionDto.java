package br.com.proenix.toolsChallenge.dto.transaction;

public record TransactionDto(
        String transactionId,
        String card,
        DescriptionDto description,
        PaymentMethodDto paymentMethod) {
}
