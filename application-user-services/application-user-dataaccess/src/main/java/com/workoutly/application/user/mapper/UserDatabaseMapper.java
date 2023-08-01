package com.workoutly.application.user.mapper;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDatabaseMapper {

    public UserSnapshot mapUserEntityToUserSnapshot(UserEntity userEntity) {
        return new UserSnapshot(
                new UserId(UUID.fromString(userEntity.getUserId())),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                UserRole.COMMON_USER,
                userEntity.isEnabled()
        );
    }

    public UserEntity mapUserSnapshotToUserEntity(UserSnapshot snapshot) {
        return UserEntity.builder()
                .userId(snapshot.getUserId().getId())
                .username(snapshot.getUsername())
                .password(snapshot.getPassword())
                .email(snapshot.getEmail())
                .isEnabled(snapshot.isEnabled())
                .build();
    }
}
