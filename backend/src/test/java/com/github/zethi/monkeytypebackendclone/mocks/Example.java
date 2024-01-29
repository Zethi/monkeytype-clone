package com.github.zethi.monkeytypebackendclone.mocks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Example {
    private final int id;
    private final String exampleString;
    private final char exampleChar;
    private final String[] exampleStringArray;


    public Example(@JsonProperty("id") int id,
                   @JsonProperty("exampleString") String exampleString,
                   @JsonProperty("exampleChar") char exampleChar,
                   @JsonProperty("exampleStringArray") String[] exampleStringArray) {
        this.id = id;
        this.exampleString = exampleString;
        this.exampleChar = exampleChar;
        this.exampleStringArray = exampleStringArray;
    }

    public int getId() {
        return id;
    }

    public String getExampleString() {
        return exampleString;
    }

    public char getExampleChar() {
        return exampleChar;
    }

    public String[] getExampleStringArray() {
        return exampleStringArray;
    }
}
