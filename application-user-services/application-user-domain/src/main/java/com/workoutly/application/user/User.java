package com.workoutly.application.user;

import com.workoutly.application.user.VO.UserRole;
import com.workoutly.common.entity.AggregateRoot;

import java.util.UUID;

public class User extends AggregateRoot<String> {
    private String username;
    private String password;
    private String email;
    private UserRole userRole;
    private boolean isEnabled;

    public User(String username, String password, String email, UserRole userRole, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.isEnabled = isEnabled;
    }

    public void initialize() {
        setId(UUID.randomUUID().toString());
    }
}