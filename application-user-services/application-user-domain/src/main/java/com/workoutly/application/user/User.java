package com.workoutly.application.user;

import com.workoutly.application.user.VO.*;
import com.workoutly.common.entity.AggregateRoot;

import java.util.UUID;

import static com.workoutly.application.user.VO.UserSnapshot.Builder.anUserSnapshot;

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

    public void enableUser() {
        this.isEnabled = true;
    }

    public void changePassword(String password) {
        this.password = new Password(password);
    }

    public void changeEmail(String email) {
        this.email = new EmailAddress(email);
    }

    public Password getPassword() {
        return password;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public UserSnapshot createSnapshot() {
        return anUserSnapshot()
                .withUsername(username.getValue())
                .withPassword(password.getValue())
                .withEmail(email.getValue())
                .withRole(userRole)
                .withIsEnabled(isEnabled)
                .withUserId(getId())
                .build();
    }
}
