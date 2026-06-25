package br.com.proenix.toolsChallenge.dto.transaction;

public record PaymentMethodDto(
        String paymentType,
        Integer installments) {
}
