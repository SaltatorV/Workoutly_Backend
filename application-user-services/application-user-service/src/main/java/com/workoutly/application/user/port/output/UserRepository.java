package com.workoutly.application.user.port.output;

import com.workoutly.application.user.VO.UserSnapshot;

import java.util.Optional;

public interface UserRepository {
    public Optional<UserSnapshot> findByUsername(String username);
}
