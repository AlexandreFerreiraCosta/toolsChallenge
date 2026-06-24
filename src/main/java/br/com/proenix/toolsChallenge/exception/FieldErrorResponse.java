package br.com.proenix.toolsChallenge.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErrorResponse {
    private String field;
    private String message;
    private Object rejectedValue;
}
