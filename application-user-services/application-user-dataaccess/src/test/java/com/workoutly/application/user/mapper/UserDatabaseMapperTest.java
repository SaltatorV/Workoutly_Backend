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
        UserEntity entity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .username("test")
                .password("password")
                .email("example@example.to")
                .isEnabled(true)
                .build();

        //when
        UserSnapshot snapshot = mapper.mapUserEntityToUserSnapshot(entity);

        //then
        assertSnapshotIsConsistentWithEntity(snapshot, entity);
    }

    private void assertSnapshotIsConsistentWithEntity(UserSnapshot mappedSnapshot, UserEntity userEntity) {
        UserSnapshot snapshot = createSnapshotFromEntity(userEntity);
        assertEquals(mapToString(snapshot), mapToString(mappedSnapshot));

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
