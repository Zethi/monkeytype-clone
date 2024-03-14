package com.github.zethi.monkeytypebackendclone.services;

import com.github.zethi.monkeytypebackendclone.entity.PasswordResetToken;
import com.github.zethi.monkeytypebackendclone.entity.User;
import com.github.zethi.monkeytypebackendclone.event.UserRequestPasswordRecoveryEvent;
import com.github.zethi.monkeytypebackendclone.exceptions.PasswordResetTokenNotFound;
import com.github.zethi.monkeytypebackendclone.repositorys.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordRecoveryService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PasswordRecoveryService(PasswordResetTokenRepository passwordResetTokenRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public PasswordResetToken generateRecoveryToken(User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        
        passwordResetToken.setExpiryDateTime(LocalDateTime.now().plusHours(1));
        passwordResetToken.setUser(user);

        this.passwordResetTokenRepository.save(passwordResetToken);
        this.applicationEventPublisher.publishEvent(new UserRequestPasswordRecoveryEvent(this, user));
        return passwordResetToken;
    }

    public void removeToken(String token) throws PasswordResetTokenNotFound {
        PasswordResetToken passwordResetToken = this.passwordResetTokenRepository.findByToken(token).orElseThrow(PasswordResetTokenNotFound::new);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    public PasswordResetToken findToken(String token) throws PasswordResetTokenNotFound {
        return this.passwordResetTokenRepository.findByToken(token).orElseThrow(PasswordResetTokenNotFound::new);
    }

    public boolean validate(String token) {
        Optional<PasswordResetToken> optionalToken = this.passwordResetTokenRepository.findByToken(token);
        return optionalToken.isPresent();
    }

    public boolean validate(PasswordResetToken passwordResetToken) {
        return passwordResetToken != null;
    }


}
