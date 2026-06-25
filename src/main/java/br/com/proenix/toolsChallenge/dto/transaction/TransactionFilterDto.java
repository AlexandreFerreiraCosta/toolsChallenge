package br.com.proenix.toolsChallenge.dto.transaction;

public record TransactionFilterDto(
        String transactionId,
        String card,
        String establishment,
        String transactionStatus,
        String paymentType) {

}
