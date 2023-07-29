package com.workoutly.application.user.adapter;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.port.output.UserRepository;
import com.workoutly.application.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;

    @Override
    public Optional<UserSnapshot> findByUsername(String username) {
        Optional<UserEntity> entity = repository.findByUsername(username);
        if(entity.isPresent()) {
            UserEntity user = entity.get();
            UserSnapshot snapshot = new UserSnapshot(
                    new UserId(UUID.fromString(user.getUserId())),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    UserRole.COMMON,
                    user.isEnabled()
            );

            return Optional.of(snapshot);
        }

        return Optional.empty();
    }
}
