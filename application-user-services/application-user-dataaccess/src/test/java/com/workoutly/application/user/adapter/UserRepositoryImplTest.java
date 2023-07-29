package com.workoutly.application.user.adapter;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    public void testFindByUsername() {

        //given
        UserEntity entity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .username("test")
                .password("password")
                .email("example@example.to")
                .isEnabled(true)
                .build();

        doReturn(Optional.of(entity)).when(userJpaRepository).findByUsername("test");

        //when
        Optional<UserSnapshot> foundSnapshot = userRepository.findByUsername("test");

        //then
        assertIsUserSnapshotValid(entity, foundSnapshot);
    }

    @Test
    public void testFindByUsernameIsEmpty() {

        //given
        doReturn(Optional.empty()).when(userJpaRepository).findByUsername("test");

        //when
        Optional<UserSnapshot> foundSnapshot = userRepository.findByUsername("test");

        //then
        assertIsUserSnapshotEmpty(foundSnapshot);
    }

    private void assertIsUserSnapshotValid(UserEntity userEntity, Optional<UserSnapshot> snapshot) {
        assertEquals(createSnapshot(userEntity), snapshot.get());
    }

    private UserSnapshot createSnapshot(UserEntity user) {
        return new UserSnapshot(
                new UserId(UUID.fromString(user.getUserId())),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                UserRole.COMMON,
                user.isEnabled()
        );
    }

    private void assertIsUserSnapshotEmpty(Optional<UserSnapshot> snapshot) {
        assertTrue(snapshot.isEmpty());
    }
}