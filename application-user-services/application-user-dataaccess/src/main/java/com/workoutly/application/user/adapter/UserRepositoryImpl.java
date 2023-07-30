package com.workoutly.application.user.adapter;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.exception.UserNotFoundException;
import com.workoutly.application.user.mapper.UserDatabaseMapper;
import com.workoutly.application.user.port.output.UserRepository;
import com.workoutly.application.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserDatabaseMapper mapper;

    @Override
    public Optional<UserSnapshot> findByUsername(String username) {
        Optional<UserEntity> entity = repository.findByUsername(username);

        if(entity.isEmpty()) {
            throw new UserNotFoundException();
        }

        UserSnapshot snapshot = mapper.mapUserEntityToUserSnapshot(entity.get());

        return Optional.of(snapshot);
    }
}
