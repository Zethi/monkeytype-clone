package com.github.zethi.monkeytypebackendclone.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DictionaryAlreadyExistsException extends Exception {

    public DictionaryAlreadyExistsException() {
    }

    public DictionaryAlreadyExistsException(String message) {
        super(message);
    }
}
