package com.workoutly.application.user.mapper;

import com.workoutly.application.user.VO.*;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.entity.VerificationTokenEntity;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDatabaseMapperTest {

    private UserDatabaseMapper mapper = new UserDatabaseMapper();

    @Test
    public void mapUserEntityToUserSnapshot() {
        //given
        var entity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .username("test")
                .password("password")
                .email("example@example.to")
                .isEnabled(true)
                .token(createToken())
                .build();

        //when
        var snapshot = mapper.mapUserEntityToUserSnapshot(entity);

        //then
        assertSnapshotIsConsistentWithEntity(snapshot, entity);
    }

    @Test
    public void mapUserSnapshotToUserEntity() {
        //given
        var entity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .username("test")
                .password("password")
                .email("example@example.to")
                .isEnabled(true)
                .token(createToken())
                .build();

        var snapshot = createSnapshotFromEntity(entity);

        //when
        var mappedEntity = mapper.mapUserSnapshotToUserEntity(snapshot);

        //then
        assertEntitiesAreConsistent(mappedEntity, entity);
    }

    private void assertSnapshotIsConsistentWithEntity(UserSnapshot mappedSnapshot, UserEntity userEntity) {
        UserSnapshot snapshot = createSnapshotFromEntity(userEntity);
        compareObjectsStrings(snapshot, mappedSnapshot);
    }

    private void assertEntitiesAreConsistent(UserEntity mappedEntity, UserEntity entity) {
        compareObjectsStrings(entity, mappedEntity);
    }

    private void compareObjectsStrings(Object expected, Object actual) {
        assertEquals(expected, actual);
    }

    private UserSnapshot createSnapshotFromEntity(UserEntity userEntity) {
        return new UserSnapshot(
                new UserId(UUID.fromString(userEntity.getUserId())),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                UserRole.COMMON_USER,
                userEntity.isEnabled(),
                createTokenSnapshot(userEntity.getToken())
        );
    }

    private VerificationTokenSnapshot createTokenSnapshot(VerificationTokenEntity entity) {
        return new VerificationTokenSnapshot(
                new TokenId(UUID.fromString(entity.getId())),
                entity.getToken(),
                entity.getExpireDate()
        );
    }

    private VerificationTokenEntity createToken() {
        return VerificationTokenEntity.builder()
                .id(UUID.randomUUID().toString())
                .token(UUID.randomUUID().toString())
                .expireDate(Date.from(Instant.now()))
                .build();
    }

}
