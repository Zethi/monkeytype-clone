package com.github.zethi.monkeytypebackendclone.event;

import com.github.zethi.monkeytypebackendclone.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserPasswordChangeEvent extends ApplicationEvent {
    private final User user;

    public UserPasswordChangeEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
