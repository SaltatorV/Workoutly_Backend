package com.workoutly.application.user.adapter;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.port.output.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<UserSnapshot> findByUsername(String username) {
        return Optional.empty();
    }
}
