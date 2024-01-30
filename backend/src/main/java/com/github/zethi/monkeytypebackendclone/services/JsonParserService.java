package com.github.zethi.monkeytypebackendclone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zethi.monkeytypebackendclone.entity.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public final class JsonParserService {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonParserService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T jsonToClass(JSON json, Class<T> type) throws JsonProcessingException {
        return objectMapper.readValue(json.toString(), type);
    }

    public <T> JSON classToJson(T instance) throws JsonProcessingException {
        String rawJson = objectMapper.writeValueAsString(instance);
        JsonNode nodeJson = objectMapper.readTree(rawJson);

        JSON json = new JSON(objectMapper, nodeJson);

        return json;
    }

    public JSON stringToJson(String data) throws JsonProcessingException {
        JSON json = new JSON(objectMapper, objectMapper.readTree(data));

        return json;
    }


}
