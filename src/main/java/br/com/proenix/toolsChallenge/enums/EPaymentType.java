package br.com.proenix.toolsChallenge.enums;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPaymentType {
    CASH("Avista"),
    STORE_INSTALLMENTS("Parcelado loja"),
    ISSUER_INSTALLMENTS("Parcelado emissor");

    private final String description;

    public static Optional<EPaymentType> getByName(String name) {
        for (EPaymentType ePaymentType : EPaymentType.values()) {
            if (ePaymentType.name().equals(name))
                return Optional.of(ePaymentType);
        }

        return Optional.empty();
    }
}
