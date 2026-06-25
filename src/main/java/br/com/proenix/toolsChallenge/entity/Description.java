package br.com.proenix.toolsChallenge.entity;

import br.com.proenix.toolsChallenge.enums.ETransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Description {
    @Column(name = "value", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "establishment")
    private String establishment;

    @Column(name = "nsu")
    private String nsu;

    @Column(name = "authorization_code")
    private String authorizationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ETransactionStatus transactionStatus;
}
