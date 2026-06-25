package br.com.proenix.toolsChallenge.unit.util.deserializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.proenix.toolsChallenge.util.deserializer.RemoveSpecialCharacterDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.core.JsonParser;

@ExtendWith(MockitoExtension.class)
class RemoveSpecialCharacterDeserializerTest {
    private final RemoveSpecialCharacterDeserializer deserializer = new RemoveSpecialCharacterDeserializer();

    @Mock
    private JsonParser jsonParser;

    @Test
    @DisplayName("should remove special characters keeping alphanumeric")
    void shouldRemoveSpecialCharactersKeepingAlphanumeric() {
        when(jsonParser.getString()).thenReturn("Hello@World!");

        String result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualTo("HelloWorld");
    }

    @Test
    @DisplayName("should return null when result is empty after removal")
    void shouldReturnNullWhenResultIsEmpty() {
        when(jsonParser.getString()).thenReturn("@#$%");

        String result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("should return same value when input has no special characters")
    void shouldReturnSameValueWhenNoSpecialCharacters() {
        when(jsonParser.getString()).thenReturn("CASH123");

        String result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualTo("CASH123");
    }

    @Test
    @DisplayName("should remove spaces and special characters")
    void shouldRemoveSpacesAndSpecialCharacters() {
        when(jsonParser.getString()).thenReturn("Parcelado loja");

        String result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualTo("Parceladoloja");
    }
}
