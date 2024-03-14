package com.github.zethi.monkeytypebackendclone.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForgotPasswordRequest(@Email(message = "Email field is obligatory") String email) {
}