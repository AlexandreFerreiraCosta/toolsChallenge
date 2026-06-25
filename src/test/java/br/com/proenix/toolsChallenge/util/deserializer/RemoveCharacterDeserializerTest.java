package br.com.proenix.toolsChallenge.util.deserializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.core.JsonParser;

@ExtendWith(MockitoExtension.class)
class RemoveCharacterDeserializerTest {
    private final RemoveCharacterDeserializer deserializer = new RemoveCharacterDeserializer();;

    @Mock
    private JsonParser jsonParser;

    @Test
    @DisplayName("should remove non-numeric characters")
    void shouldRemoveNonNumericCharacters() {
        when(jsonParser.getString()).thenReturn("abc123def456");

        String result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualTo("123456");
    }

    @Test
    @DisplayName("should return null when result is empty after removal")
    void shouldReturnNullWhenResultIsEmpty() {
        when(jsonParser.getString()).thenReturn("abc");

        String result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("should return digits only when input has special characters")
    void shouldReturnDigitsOnlyWithSpecialCharacters() {
        when(jsonParser.getString()).thenReturn("12.345-67");

        String result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualTo("1234567");
    }

    @Test
    @DisplayName("should return same value when input is already numeric")
    void shouldReturnSameValueWhenAlreadyNumeric() {
        when(jsonParser.getString()).thenReturn("123456");

        String result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualTo("123456");
    }
}
