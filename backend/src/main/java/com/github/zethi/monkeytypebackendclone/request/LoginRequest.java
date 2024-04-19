package com.github.zethi.monkeytypebackendclone.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(@NotBlank(message = "Username field is obligatory") String username,
                           @NotBlank(message = "Password field is obligatory") String password) {
}