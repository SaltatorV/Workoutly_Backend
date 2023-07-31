package com.workoutly.application.user.port.output;

import com.workoutly.application.user.VO.UserSnapshot;

public interface UserRepository {
    public UserSnapshot findByUsername(String username);
    public UserSnapshot save(UserSnapshot snapshot);
}
