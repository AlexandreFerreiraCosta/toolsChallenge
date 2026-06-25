package br.com.proenix.toolsChallenge.util.deserializer;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class RemoveCharacterDeserializer extends ValueDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser,DeserializationContext deserializationContext) {
        var result = jsonParser.getString().replaceAll("\\D+","");

        return result.isEmpty() ? null : result;
    }
}
