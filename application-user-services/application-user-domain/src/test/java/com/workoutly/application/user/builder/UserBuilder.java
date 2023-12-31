package com.workoutly.application.user.builder;

import com.workoutly.application.user.User;
import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VerificationToken;

public class UserBuilder {
    private UserId userId;
    private String username;
    private String password;
    private String email;
    private UserRole role;
    private VerificationToken token;

    public static UserBuilder anUser() {
        return new UserBuilder();
    }
    public UserBuilder withUserId(UserId userId) {
        this.userId = userId;
        return this;
    }


    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withRole(UserRole role) {
        this.role = role;
        return this;
    }

    public UserBuilder withVerificationToken(VerificationToken token) {
        this.token = token;
        return this;
    }

    public User build() {
        User user = new User(username, password, email, role, token);
        user.setId(userId);
        return user;
    }

    public User buildEnabled() {
        User user = build();
        user.enable();
        return user;
    }

    public User buildInitialized() {
        User user = new User(username, password, email, role, token);
        user.initialize();
        return user;
    }
}