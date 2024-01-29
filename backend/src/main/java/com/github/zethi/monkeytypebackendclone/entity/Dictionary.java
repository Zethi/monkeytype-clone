package com.github.zethi.monkeytypebackendclone.entity;

public final class Dictionary {

    private String name;

    private String[] words;

    public Dictionary(String name, String[] words) {
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
