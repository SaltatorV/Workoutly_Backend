package com.workoutly.application.user;

import com.workoutly.application.user.VO.*;
import com.workoutly.common.entity.AggregateRoot;

import java.util.UUID;

public class User extends AggregateRoot<UserId> {
    private Username username;
    private Password password;
    private EmailAddress email;
    private UserRole userRole;
    private boolean isEnabled;

    public User(Username username, Password password, EmailAddress email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

    public void initialize() {
        setId(new UserId(UUID.randomUUID()));
        isEnabled = false;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
