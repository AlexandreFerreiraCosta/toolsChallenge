package br.com.proenix.toolsChallenge.enums;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ETransactionStatus {
    PROCESSING("Processando"),
    AUTHORIZED("Autorizado"),
    DENIED("Negado");

    private final String description;

    public static Optional<ETransactionStatus> getByName(String name) {
        for (ETransactionStatus eTransactionStatus : ETransactionStatus.values()) {
            if (eTransactionStatus.name().equals(name))
                return Optional.of(eTransactionStatus);
        }

        return Optional.empty();
    }
}
