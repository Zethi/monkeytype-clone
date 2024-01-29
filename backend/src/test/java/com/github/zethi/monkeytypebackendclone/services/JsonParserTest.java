package com.github.zethi.monkeytypebackendclone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.zethi.monkeytypebackendclone.entity.JSON;
import com.github.zethi.monkeytypebackendclone.exceptions.JsonNodeIsNotAObjectException;
import com.github.zethi.monkeytypebackendclone.mocks.Example;
import com.github.zethi.monkeytypebackendclone.utils.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class JsonParserTest {

    private final JsonParser jsonParser;

    @Autowired
    public JsonParserTest(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    @Test
    @DisplayName("Parse a Object of any type to a JSON")
    public void parseObjectToJSONData() throws JsonProcessingException {
        Example example = new Example(1, "example", 'A', new String[]{"one", "two"});
        JSON json = jsonParser.classToJson(example);

        Assertions.assertEquals(
                "{\"id\":1,\"exampleString\":\"example\",\"exampleChar\":\"A\",\"exampleStringArray\":[\"one\",\"two\"]}",
                json.toString()
        );

        Assertions.assertInstanceOf(JSON.class, json);
    }

    @Test
    @DisplayName("Parse a JSON to Object")
    public void parseJsonToObject(@Autowired JSON json) throws JsonProcessingException, JsonNodeIsNotAObjectException {
        json.setField("id", 1);
        json.setField("exampleString", "example");
        json.setField("exampleChar", 'A');
        json.setField("exampleStringArray", new String[]{"one", "two"});

        Example example = jsonParser.jsonToClass(json, Example.class);

        Assertions.assertEquals(1, example.getId());
        Assertions.assertEquals("example", example.getExampleString());
        Assertions.assertEquals('A', example.getExampleChar());
        Assertions.assertArrayEquals(new String[]{"one", "two"}, example.getExampleStringArray());
    }

}
