package br.com.proenix.toolsChallenge.util.deserializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.exc.InvalidFormatException;

public class MoneyDeserializer extends ValueDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser jsonParser,DeserializationContext deserializationContext) {
        String value = jsonParser.getString();
        try {
            return new BigDecimal(value).setScale(2,RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw InvalidFormatException.from(jsonParser,"Valor monetário inválido: '" + value + "'",value,BigDecimal.class);
        }
    }
}
