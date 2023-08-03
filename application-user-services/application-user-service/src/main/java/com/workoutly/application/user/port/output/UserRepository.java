package com.workoutly.application.user.port.output;

import com.workoutly.application.user.VO.UserSnapshot;

import java.util.Optional;

public interface UserRepository {
    UserSnapshot findByUsername(String username);
    UserSnapshot save(UserSnapshot snapshot);
    boolean checkUserExists(UserSnapshot snapshot);
    Optional<UserSnapshot> findByVerificationToken(String token);
}
