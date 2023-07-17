package com.workoutly.application.user.utils;

import com.workoutly.application.user.VO.*;

public class UserSnapshotBuilder {
    private UserId userId;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private boolean isEnabled;

    public static UserSnapshotBuilder aUserSnapshot() {
        return new UserSnapshotBuilder();
    }

    public UserSnapshotBuilder withId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public UserSnapshotBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserSnapshotBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserSnapshotBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserSnapshotBuilder withRole(UserRole role) {
        this.role = role;
        return this;
    }

    public UserSnapshotBuilder isEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public UserSnapshot build() {
        return new UserSnapshot(userId, username, email, password, role, isEnabled);
    }

}
