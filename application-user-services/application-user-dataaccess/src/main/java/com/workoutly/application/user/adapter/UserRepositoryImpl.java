package com.workoutly.application.user.adapter;

import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.entity.UserRoleEntity;
import com.workoutly.application.user.exception.UserNotFoundException;
import com.workoutly.application.user.mapper.UserDatabaseMapper;
import com.workoutly.application.user.port.output. UserRepository;
import com.workoutly.application.user.repository.UserJpaRepository;
import com.workoutly.application.user.repository.UserRoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserRoleJpaRepository userRoleJpaRepository;
    private final UserDatabaseMapper mapper;

    @Override
    public UserSnapshot findByUsername(String username) {
        Optional<UserEntity> entity = userJpaRepository.findByUsername(username);

        if(entity.isEmpty()) {
            throw new UserNotFoundException();
        }

        UserSnapshot snapshot = mapper.mapUserEntityToUserSnapshot(entity.get());

        return snapshot;
    }

    @Override
    public UserSnapshot save(UserSnapshot snapshot) {
        UserEntity entityToSave = mapper.mapUserSnapshotToUserEntity(snapshot);
        UserRoleEntity role = userRoleJpaRepository.getRoleEntityByPermissionName(snapshot.getRole().getRoleName());

        entityToSave.setRole(role);

        UserEntity savedEntity = userJpaRepository.save(entityToSave);

        return mapper.mapUserEntityToUserSnapshot(savedEntity);
    }

    @Override
    public boolean checkUserExists(UserSnapshot snapshot) {
        return isUsernameExists(snapshot.getUsername()) || isEmailExists(snapshot.getEmail());
    }

    private boolean isUsernameExists(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    private boolean isEmailExists(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
