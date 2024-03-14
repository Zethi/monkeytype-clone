package com.github.zethi.monkeytypebackendclone.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SignUpRequest(@Email(message = "Email field is obligatory") @Size(max = 320) String email,
                            @NotBlank(message = "Username field is obligatory") @Size(min = 5, max = 40) String username,
                            @NotBlank(message = "Password field is obligatory") @Size(min = 5, max = 60) String password) {
}