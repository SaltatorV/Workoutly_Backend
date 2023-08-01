package com.workoutly.application.user.port.output;

import com.workoutly.application.user.VO.UserSnapshot;

public interface UserRepository {
    UserSnapshot findByUsername(String username);
    UserSnapshot save(UserSnapshot snapshot);
    boolean checkUserUniqueness(UserSnapshot snapshot);
}
