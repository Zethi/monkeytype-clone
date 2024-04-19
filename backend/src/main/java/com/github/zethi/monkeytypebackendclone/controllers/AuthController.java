package com.github.zethi.monkeytypebackendclone.controllers;

import com.github.zethi.monkeytypebackendclone.entity.PasswordResetToken;
import com.github.zethi.monkeytypebackendclone.entity.User;
import com.github.zethi.monkeytypebackendclone.exceptions.InvalidPasswordResetTokenException;
import com.github.zethi.monkeytypebackendclone.exceptions.PasswordResetTokenNotFound;
import com.github.zethi.monkeytypebackendclone.exceptions.UserNotFoundException;
import com.github.zethi.monkeytypebackendclone.request.LoginRequest;
import com.github.zethi.monkeytypebackendclone.request.SignUpRequest;
import com.github.zethi.monkeytypebackendclone.services.AuthService;
import com.github.zethi.monkeytypebackendclone.services.PasswordRecoveryService;
import com.github.zethi.monkeytypebackendclone.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;

    @Autowired
    public AuthController(AuthService authService, UserService userService, PasswordRecoveryService passwordRecoveryService) {
        this.authService = authService;
        this.userService = userService;
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        final String token = authService.login(loginRequest.username(), loginRequest.password());

        Map<String, String> body = new HashMap<>();
        body.put("message", "logged successfully");
        body.put("token", token);

        return ResponseEntity.ok(body);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@Valid @RequestBody SignUpRequest signUpRequest, UriComponentsBuilder uriComponentsBuilder) {
        final User registeredUser = authService.register(signUpRequest.email(), signUpRequest.username(), signUpRequest.password());

        final URI locationOfNewUser = uriComponentsBuilder
                .path("api/users/{id}")
                .buildAndExpand(registeredUser.getId())
                .toUri();

        Map<String, String> body = new HashMap<>();
        body.put("message", "Account registered successfully");

        return ResponseEntity.created(locationOfNewUser).body(body);
    }

    @PostMapping("/forgot-password/{token}")
    public ResponseEntity<Map<String, String>> changePasswordFromForgotPassword(@PathVariable String token, @RequestBody String newPassword) throws PasswordResetTokenNotFound, UserNotFoundException, InvalidPasswordResetTokenException {
        authService.changePassword(newPassword, token);

        Map<String, String> body = new HashMap<>();
        body.put("message", "Password changed successfully");

        return ResponseEntity.ok(body);
    }

    @GetMapping("/forgot-password/{email}")
    public ResponseEntity<Map<String, String>> generateTokenToResetPassword(@PathVariable String email) throws UserNotFoundException {
        final User user = this.userService.getByEmail(email).orElseThrow(UserNotFoundException::new);
        final PasswordResetToken passwordResetToken = passwordRecoveryService.generateRecoveryToken(user);

        Map<String, String> body = new HashMap<>();
        body.put("message", "Email sent");
        body.put("token", passwordResetToken.getToken());

        return ResponseEntity.ok(body);
    }

}
