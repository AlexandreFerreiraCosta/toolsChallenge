package br.com.proenix.toolsChallenge.entity;

import br.com.proenix.toolsChallenge.enums.EPaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PaymentMethod {
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type", nullable = false)
    private EPaymentType paymentType;

    @Column(name = "installments")
    private Integer installments;
}
