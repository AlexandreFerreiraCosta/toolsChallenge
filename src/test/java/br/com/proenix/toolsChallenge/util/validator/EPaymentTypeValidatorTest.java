package br.com.proenix.toolsChallenge.util.validator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EPaymentTypeValidatorTest {
    private final EPaymentTypeValidator validator = new EPaymentTypeValidator();;

    @Test
    @DisplayName("should return false when value is null")
    void shouldReturnFalseWhenValueIsNull() {
        assertThat(validator.isValid(null,null)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("should return true for valid payment types")
    @ValueSource(strings = {"CASH","STORE_INSTALLMENTS","ISSUER_INSTALLMENTS"})
    void shouldReturnTrueForValidPaymentTypes(String value) {
        assertThat(validator.isValid(value,null)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("should return false for invalid payment types")
    @ValueSource(strings = {"INVALID","PIX","BOLETO",""})
    void shouldReturnFalseForInvalidPaymentTypes(String value) {
        assertThat(validator.isValid(value,null)).isFalse();
    }

    @Test
    @DisplayName("should strip special characters before validation")
    void shouldStripSpecialCharactersBeforeValidation() {
        assertThat(validator.isValid("STORE_INSTALLMENTS",null)).isTrue();
    }
}
