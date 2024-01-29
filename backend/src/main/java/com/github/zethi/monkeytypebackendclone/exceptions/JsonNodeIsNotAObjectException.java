package com.github.zethi.monkeytypebackendclone.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class JsonNodeIsNotAObjectException extends Exception {
}
