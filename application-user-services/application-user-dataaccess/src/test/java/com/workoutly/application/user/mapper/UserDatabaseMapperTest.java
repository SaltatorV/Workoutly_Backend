package com.workoutly.application.user.mapper;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.workoutly.application.user.utils.TestUtils.mapToString;
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
        assertEquals(mapToString(expected), mapToString(actual));
    }

    private UserSnapshot createSnapshotFromEntity(UserEntity userEntity) {
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
