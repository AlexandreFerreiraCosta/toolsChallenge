package br.com.proenix.toolsChallenge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ETransactionStatus {
    AUTHORIZED("Autorizado"),
    DENIED("Negado"),
    REVERSED("Estornado");

    private final String description;
}
