package br.com.proenix.toolsChallenge.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"dateError","status","error","message","path","errors"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private LocalDateTime dateError;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldErrorResponse> errors;
}
