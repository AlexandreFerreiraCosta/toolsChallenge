package br.com.proenix.toolsChallenge.util.deserializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.core.JsonParser;

@ExtendWith(MockitoExtension.class)
class MoneyDeserializerTest {
    private final MoneyDeserializer deserializer = new MoneyDeserializer();;

    @Mock
    private JsonParser jsonParser;

    @Test
    @DisplayName("should deserialize value and scale to 2 decimal places")
    void shouldDeserializeAndScaleToTwoDecimalPlaces() {
        when(jsonParser.getString()).thenReturn("100.999");

        BigDecimal result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualByComparingTo(new BigDecimal("101.00"));
        assertThat(result.scale()).isEqualTo(2);
    }

    @Test
    @DisplayName("should deserialize integer value and set scale to 2")
    void shouldDeserializeIntegerAndSetScaleToTwo() {
        when(jsonParser.getString()).thenReturn("500");

        BigDecimal result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualByComparingTo(new BigDecimal("500.00"));
        assertThat(result.scale()).isEqualTo(2);
    }

    @Test
    @DisplayName("should deserialize value already with 2 decimal places")
    void shouldDeserializeValueWithTwoDecimalPlaces() {
        when(jsonParser.getString()).thenReturn("123.45");

        BigDecimal result = deserializer.deserialize(jsonParser, null);

        assertThat(result).isEqualByComparingTo(new BigDecimal("123.45"));
    }
}
