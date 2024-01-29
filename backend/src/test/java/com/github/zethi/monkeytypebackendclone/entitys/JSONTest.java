package com.github.zethi.monkeytypebackendclone.entitys;

import com.github.zethi.monkeytypebackendclone.entity.JSON;
import com.github.zethi.monkeytypebackendclone.exceptions.JsonNodeIsNotAObjectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public final class JSONTest {

    private final JSON json;

    @Autowired
    public JSONTest(JSON json) throws JsonNodeIsNotAObjectException {
        this.json = json;
        json.setField("exampleString", "This is a test");
        json.setField("exampleBoolean", true);
        json.setField("exampleNumber", 100);
        json.setField("exampleArray", new String[]{"one", "two"});
    }

    @Test
    @DisplayName("Get string from JSON")
    public void shouldGetAStringFromJson() {
        Assertions.assertEquals("This is a test", json.getValue("exampleString", String.class).orElse(""));
    }

    @Test
    @DisplayName("Get boolean from JSON")
    public void shouldGetABooleanFromJson() {
        Assertions.assertEquals(true, json.getValue("exampleBoolean", Boolean.class).orElse(false));
    }

    @Test
    @DisplayName("Get number from JSON")
    public void shouldGetANumberFromJson() {
        Assertions.assertEquals(100, json.getValue("exampleNumber", Number.class).orElse(0));
    }

    @Test
    @DisplayName("Get string array from JSON")
    public void shouldGetAStringArrayFromJson() {
        Assertions.assertArrayEquals(new String[]{"one", "two"}, json.getValue("exampleArray", String[].class).orElse(new String[]{}));
    }

}
