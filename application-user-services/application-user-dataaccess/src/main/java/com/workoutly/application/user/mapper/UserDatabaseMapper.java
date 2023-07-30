package com.workoutly.application.user.mapper;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.entity.UserEntity;

import java.util.UUID;

public class UserDatabaseMapper {

    public UserSnapshot mapUserEntityToUserSnapshot(UserEntity userEntity) {
        return new UserSnapshot(
                new UserId(UUID.fromString(userEntity.getUserId())),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                UserRole.COMMON,
                userEntity.isEnabled()
        );
    }
}
