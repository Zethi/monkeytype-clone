package com.github.zethi.monkeytypebackendclone.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DictionaryNotFoundException extends Exception {

    public DictionaryNotFoundException() {
    }

    public DictionaryNotFoundException(String message) {
        super(message);
    }
}
