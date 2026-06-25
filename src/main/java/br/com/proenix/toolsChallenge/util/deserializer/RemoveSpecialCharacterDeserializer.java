package br.com.proenix.toolsChallenge.util.deserializer;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class RemoveSpecialCharacterDeserializer extends ValueDeserializer<String> {
    private static final String REGEX_REMOVE_SPECIAL_CHARACTER = "[^a-zA-Z0-9]";

    @Override
    public String deserialize(JsonParser jsonParser,DeserializationContext deserializationContext) {
        String result = jsonParser.getString().replaceAll(REGEX_REMOVE_SPECIAL_CHARACTER,"");

        return result.isEmpty() ? null : result;
    }
}
