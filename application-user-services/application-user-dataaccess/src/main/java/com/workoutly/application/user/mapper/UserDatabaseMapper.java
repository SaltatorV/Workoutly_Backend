package com.workoutly.application.user.mapper;

import com.workoutly.application.user.VO.*;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.entity.VerificationTokenEntity;
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
                userEntity.isEnabled(),
                mapTokenEntityToSnapshot(userEntity.getToken())
        );
    }

    public UserEntity mapUserSnapshotToUserEntity(UserSnapshot snapshot) {
        return UserEntity.builder()
                .userId(snapshot.getUserId().getId())
                .username(snapshot.getUsername())
                .password(snapshot.getPassword())
                .email(snapshot.getEmail())
                .isEnabled(snapshot.isEnabled())
                .token(mapTokenSnapshotToEntity(snapshot))
                .build();
    }

    private VerificationTokenSnapshot mapTokenEntityToSnapshot(VerificationTokenEntity entity) {
        return new VerificationTokenSnapshot(new TokenId(UUID.fromString(entity.getId())),
                entity.getToken(),
                entity.getExpireDate());
    }

    private VerificationTokenEntity mapTokenSnapshotToEntity(UserSnapshot userSnapshot) {
        VerificationTokenSnapshot snapshot = userSnapshot.getToken();

        return VerificationTokenEntity
                .builder()
                .id(snapshot.getTokenId().getId())
                .token(snapshot.getToken())
                .expireDate(snapshot.getExpireTime())
                .build();
    }
}
