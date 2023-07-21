package com.workoutly.application.user;

import com.workoutly.application.user.VO.*;
import com.workoutly.common.entity.AggregateRoot;

import java.util.UUID;

import static com.workoutly.application.user.VO.UserSnapshot.Builder.anUserSnapshot;

public class User extends AggregateRoot<UserId> {
    private String username;
    private String password;
    private String email;
    private UserRole userRole;
    private boolean isEnabled;

    public User(String username, String password, String email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

    public User(UserId userId, String username, String password, String email, UserRole userRole, boolean isEnabled) {
        this(username, password, email, userRole);
        setId(userId);
        this.isEnabled = isEnabled;
    }

    public static User restore(UserSnapshot snapshot) {
        return new User(snapshot.getUserId(),
                snapshot.getUsername(),
                snapshot.getPassword(),
                snapshot.getEmail(),
                snapshot.getRole(),
                snapshot.isEnabled());
    }

    public void initialize() {
        setId(new UserId(UUID.randomUUID()));
        isEnabled = false;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void enable() {
        this.isEnabled = true;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public UserSnapshot createSnapshot() {
        return anUserSnapshot()
                .withUsername(username)
                .withPassword(password)
                .withEmail(email)
                .withRole(userRole)
                .withIsEnabled(isEnabled)
                .withUserId(getId())
                .build();
    }
}
