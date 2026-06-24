package br.com.proenix.toolsChallenge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ETransactionStatus {
    AUTHORIZED("Autorizado"),
    DENIED("Negado");

    private final String description;
}
