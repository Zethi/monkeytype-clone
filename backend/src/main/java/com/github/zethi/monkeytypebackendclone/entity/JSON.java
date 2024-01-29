package com.github.zethi.monkeytypebackendclone.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.zethi.monkeytypebackendclone.exceptions.JsonNodeIsNotAObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component
public final class JSON {

    private final ObjectMapper objectMapper;
    private JsonNode properties;

    @Autowired
    public JSON(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.properties = objectMapper.createObjectNode();
    }


    public JSON(ObjectMapper objectMapper, JsonNode jsonNode) {
        this.objectMapper = objectMapper;
        this.properties = jsonNode;
    }

    @JsonAnySetter
    public <T> void setField(String field, T value) throws JsonNodeIsNotAObjectException {
        if (!(properties instanceof ObjectNode objectNode)) throw new JsonNodeIsNotAObjectException();

        JsonNode jsonValue = objectMapper.valueToTree(value);

        objectNode.set(field, jsonValue);
    }

    public <T> Optional<T> getValue(String key, Class<T> type) {
        try {
            return Optional.of(objectMapper.readValue(properties.get(key).traverse(), type));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return properties.toString();
    }
}
