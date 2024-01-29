package com.github.zethi.monkeytypebackendclone.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Dictionary {

    private final String name;

    private final String[] words;

    public Dictionary(@JsonProperty("name") String name, @JsonProperty("words") String[] words) {
        this.name = name;
        this.words = words;
    }

    public String getName() {
        return name;
    }

    public String[] getWords() {
        return words;
    }
}
