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
    private VerificationToken token;

    public User(String username, String password, String email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        token = new VerificationToken();
    }

    public User(String username, String password, String email, UserRole userRole, VerificationToken token) {
        this(username, password, email, userRole);
        this.token = token;
    }

    public User(UserId userId, String username, String password, String email, UserRole userRole, boolean isEnabled, VerificationToken token) {
        this(username, password, email, userRole);
        setId(userId);
        this.isEnabled = isEnabled;
        this.token = token;
    }

    public static User restore(UserSnapshot snapshot) {
        return new User(snapshot.getUserId(),
                snapshot.getUsername(),
                snapshot.getPassword(),
                snapshot.getEmail(),
                snapshot.getRole(),
                snapshot.isEnabled(),
                VerificationToken.restore(snapshot.getToken()));
    }

    public void initialize() {
        setId(new UserId(UUID.randomUUID()));
        token = VerificationToken.generateToken();
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
                .withUserId(getId())
                .withUsername(username)
                .withPassword(password)
                .withEmail(email)
                .withRole(userRole)
                .withIsEnabled(isEnabled)
                .withToken(token.createTokenSnapshot())
                .build();
    }
}
