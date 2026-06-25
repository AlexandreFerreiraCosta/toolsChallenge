package br.com.proenix.toolsChallenge.unit.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.proenix.toolsChallenge.exception.ApiErrorResponse;
import br.com.proenix.toolsChallenge.exception.FailureBadRequestException;
import br.com.proenix.toolsChallenge.exception.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.core.JacksonException.Reference;
import tools.jackson.databind.exc.InvalidFormatException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler handler;

    @Mock
    private MessageSource messageSource;

    @Mock
    private HttpServletRequest request;

    @Mock
    InvalidFormatException invalidFormatException;

    @Mock
    Reference reference;

    @Mock
    BindingResult bindingResult;

    @Test
    @DisplayName("should return 400 with message for FailureBadRequestException")
    void shouldReturn400ForFailureBadRequestException() {
        when(request.getRequestURI()).thenReturn("/api/transactions");

        FailureBadRequestException failureBadRequestException = new FailureBadRequestException("transação já existe.");
        ResponseEntity<ApiErrorResponse> response = handler.badRequestException(failureBadRequestException, request);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDateError()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Bad Request");
        assertThat(response.getBody().getMessage()).isEqualTo("transação já existe.");
        assertThat(response.getBody().getPath()).isEqualTo("/api/transactions");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getErrors()).isNull();
    }

    @Test
    @DisplayName("should return 400 with field errors for MethodArgumentNotValidException")
    void shouldReturn400WithFieldErrorsForMethodArgumentNotValidException() {
        when(request.getRequestURI()).thenReturn("/api/transactions");
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Validation error");

        FieldError fieldError = new FieldError("dto", "card", null, false, null, null, "o valor não pode ser nulo.");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ApiErrorResponse> response = handler.methodArgumentNotValidException(methodArgumentNotValidException, request);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDateError()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Bad Request");
        assertThat(response.getBody().getMessage()).isEqualTo("Validation error");
        assertThat(response.getBody().getErrors().getFirst().getMessage()).isEqualTo("o valor não pode ser nulo.");
        assertThat(response.getBody().getErrors().getFirst().getField()).isEqualTo("card");
        assertThat(response.getBody().getErrors()).hasSize(1);
        assertThat(response.getBody().getPath()).isEqualTo("/api/transactions");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("should return 400 with field path for InvalidFormatException")
    void shouldReturn400WithFieldPathForInvalidFormatException() {
        when(request.getRequestURI()).thenReturn("/api/transactions");
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("o campo 'type' recebeu um valor inválido");

        when(invalidFormatException.getValue()).thenReturn("INVALID_TYPE");
        when(reference.getPropertyName()).thenReturn("paymentMethod");
        when(invalidFormatException.getPath()).thenReturn(List.of(reference));

        ResponseEntity<ApiErrorResponse> response = handler.invalidFormatException(invalidFormatException, request);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDateError()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Bad Request");
        assertThat(response.getBody().getMessage()).isEqualTo("o campo 'type' recebeu um valor inválido");
        assertThat(response.getBody().getPath()).isEqualTo("/api/transactions");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("should use 'field' as fallback when path is empty for InvalidFormatException")
    void shouldUseFallbackWhenPathIsEmptyForInvalidFormatException() {
        when(request.getRequestURI()).thenReturn("/api/transactions");
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("campo recebeu um valor inválido");

        when(invalidFormatException.getValue()).thenReturn("INVALID");
        when(invalidFormatException.getPath()).thenReturn(List.of());

        ResponseEntity<ApiErrorResponse> response = handler.invalidFormatException(invalidFormatException, request);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDateError()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Bad Request");
        assertThat(response.getBody().getMessage()).isEqualTo("campo recebeu um valor inválido");
        assertThat(response.getBody().getPath()).isEqualTo("/api/transactions");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
