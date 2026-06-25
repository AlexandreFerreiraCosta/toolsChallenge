package br.com.proenix.toolsChallenge.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.exc.InvalidFormatException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final String VALIDATION_ERROR_SUBMITTED = "validation-error-submitted";
    private static final String FIELD_RECEIVED_THE_VALUE = "field-received-the-value";
    private static final String FIELD = "field";
    private static final String REQUESTED_PATH_WAS_NOT_FOUND_ON_THE_SERVER = "requested-path-was-not-found-on-the-server";

    private final MessageSource messageSource;

    @ExceptionHandler(FailureBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> badRequestException(FailureBadRequestException failureBadRequestException,
            HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDateError(LocalDateTime.now());
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiErrorResponse.setMessage(failureBadRequestException.getMessage());
        apiErrorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException,
            HttpServletRequest request) {
        List<FieldErrorResponse> fieldErrors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    FieldErrorResponse field = new FieldErrorResponse();
                    field.setField(error.getField());
                    field.setMessage(error.getDefaultMessage());
                    field.setRejectedValue(error.getRejectedValue());
                    return field;
                })
                .toList();

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDateError(LocalDateTime.now());
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiErrorResponse.setMessage(messageSource.getMessage(VALIDATION_ERROR_SUBMITTED,null,LocaleContextHolder.getLocale()));
        apiErrorResponse.setPath(request.getRequestURI());
        apiErrorResponse.setErrors(fieldErrors);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiErrorResponse> invalidFormatException(InvalidFormatException invalidFormatException,
            HttpServletRequest request) {
        String pathCompleto = invalidFormatException.getPath().stream()
                .map(JacksonException.Reference::getPropertyName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("."));

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDateError(LocalDateTime.now());
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiErrorResponse.setMessage(messageSource.getMessage(FIELD_RECEIVED_THE_VALUE,
                new Object[]{invalidFormatException.getValue(),pathCompleto.isEmpty() ? FIELD : pathCompleto,},
                LocaleContextHolder.getLocale()));
        apiErrorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> noResourceFoundException(NoResourceFoundException noResourceFoundException,
            HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDateError(LocalDateTime.now());
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiErrorResponse
                .setMessage((messageSource.getMessage(REQUESTED_PATH_WAS_NOT_FOUND_ON_THE_SERVER,null,LocaleContextHolder.getLocale())));
        apiErrorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> constraintViolationException(ConstraintViolationException constraintViolationException,
            HttpServletRequest request) {

        List<FieldErrorResponse> fieldErrors = constraintViolationException.getConstraintViolations()
                .stream().map(violation -> {
                    FieldErrorResponse fieldError = new FieldErrorResponse();
                    fieldError.setField(violation.getPropertyPath().toString());
                    fieldError.setMessage(violation.getMessage());
                    fieldError.setRejectedValue(violation.getInvalidValue());
                    return fieldError;
                }).toList();

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDateError(LocalDateTime.now());
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiErrorResponse.setMessage(messageSource.getMessage(VALIDATION_ERROR_SUBMITTED,null,LocaleContextHolder.getLocale()));
        apiErrorResponse.setPath(request.getRequestURI());
        apiErrorResponse.setErrors(fieldErrors);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
