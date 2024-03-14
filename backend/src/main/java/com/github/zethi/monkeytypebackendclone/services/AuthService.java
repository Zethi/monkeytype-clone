package com.github.zethi.monkeytypebackendclone.services;

import com.github.zethi.monkeytypebackendclone.entity.PasswordResetToken;
import com.github.zethi.monkeytypebackendclone.entity.Stats;
import com.github.zethi.monkeytypebackendclone.entity.User;
import com.github.zethi.monkeytypebackendclone.event.UserLoginEvent;
import com.github.zethi.monkeytypebackendclone.event.UserPasswordChangeEvent;
import com.github.zethi.monkeytypebackendclone.event.UserSingUpEvent;
import com.github.zethi.monkeytypebackendclone.exceptions.InvalidPasswordResetTokenException;
import com.github.zethi.monkeytypebackendclone.exceptions.PasswordResetTokenNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService, PasswordRecoveryService passwordRecoveryService, ApplicationEventPublisher applicationEventPublisher, JwtEncoder jwtEncoder, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordRecoveryService = passwordRecoveryService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    public User register(String email, String username, String password) {
        User user = new User();

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setStats(new Stats());
        user.setRoles(new HashSet<>());

        this.userService.createUser(user);

        applicationEventPublisher.publishEvent(new UserSingUpEvent(this, user));
        return user;
    }

    public String login(String username, String password) {
        final Authentication authenticationFields = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationFields);
        final User user = userService.getByUsername(username).orElseThrow();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .claim("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("email", user.getEmail())
                .issuedAt(Instant.now())
                .build();
        final JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
        final String token = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        applicationEventPublisher.publishEvent(new UserLoginEvent(this, user));
        return token;
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));

        applicationEventPublisher.publishEvent(new UserPasswordChangeEvent(this, user));
        userService.updateUser(user);
    }

    public void changePassword(String newPassword, String passwordResetToken) throws PasswordResetTokenNotFound, InvalidPasswordResetTokenException {
        final PasswordResetToken token = passwordRecoveryService.findToken(passwordResetToken);
        final boolean tokenIsValid = passwordRecoveryService.validate(token);

        if (!tokenIsValid) throw new InvalidPasswordResetTokenException();

        this.changePassword(token.getUser(), newPassword);
    }
}
