package br.com.proenix.toolsChallenge.util.deserializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class MoneyDeserializer extends ValueDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser jsonParser,DeserializationContext deserializationContext) {
        return new BigDecimal(jsonParser.getString()).setScale(2,RoundingMode.HALF_UP);
    }
}
