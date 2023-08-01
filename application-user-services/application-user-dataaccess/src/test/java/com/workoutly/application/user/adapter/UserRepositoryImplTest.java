package com.workoutly.application.user.adapter;

import com.workoutly.application.user.VO.UserId;
import com.workoutly.application.user.VO.UserRole;
import com.workoutly.application.user.VO.UserSnapshot;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.exception.ApplicationUserDomainException;
import com.workoutly.application.user.exception.UserNotFoundException;
import com.workoutly.application.user.mapper.UserDatabaseMapper;
import com.workoutly.application.user.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private UserDatabaseMapper mapper;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    public void testFindByUsername() {

        //given
        var entity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .username("test")
                .password("password")
                .email("example@example.to")
                .isEnabled(true)
                .build();

        doReturn(Optional.of(entity)).when(userJpaRepository).findByUsername("test");
        doReturn(createSnapshotFromEntity(entity)).when(mapper).mapUserEntityToUserSnapshot(entity);

        //when
        UserSnapshot foundSnapshot = userRepository.findByUsername("test");

        //then
        assertIsUserSnapshotValid(entity, foundSnapshot);
    }

    @Test
    public void testThrowExceptionWhenFindByUsername() {

        //given
        doReturn(Optional.empty()).when(userJpaRepository).findByUsername("test");

        //when
        ApplicationUserDomainException exception = throwExceptionWhenFindByUsername("test");

        //then
        assertIsExceptionAUserNotFound(exception);
    }

    @Test
    public void testSaveUser() {

        //given
        var entity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .username("test")
                .password("password")
                .email("example@example.to")
                .isEnabled(false)
                .build();

        var snapshot = createSnapshotFromEntity(entity);

        doReturn(entity)
                .when(mapper)
                .mapUserSnapshotToUserEntity(snapshot);

        doReturn(entity)
                .when(userJpaRepository)
                .save(any());

        doReturn(createSnapshotFromEntity(entity))
                .when(mapper)
                .mapUserEntityToUserSnapshot(any());

        //when
        var savedSnapshot = userRepository.save(snapshot);

        //then
        assertIsUserSnapshotValid(snapshot, savedSnapshot);
    }

    @Test
    public void testCheckUserIsUnique() {
        //given
        var snapshot = createTestCommonUserSnapshot();

        doReturn(true)
                .when(userJpaRepository)
                .existsByUsername(snapshot.getUsername());

        doReturn(true)
                .when(userJpaRepository)
                .existsByEmail(snapshot.getEmail());

        //when
        var isUnique = userRepository.checkUserUniqueness(snapshot);

        //then
        assertTrue(isUnique);
    }

    @Test
    public void testCheckUsernameIsNotUnique() {
        //given
        var snapshot = createTestCommonUserSnapshot();

        doReturn(false)
                .when(userJpaRepository)
                .existsByUsername(snapshot.getUsername());


        //when
        var isNotUnique = userRepository.checkUserUniqueness(snapshot);

        //then
        assertFalse(isNotUnique);
    }

    @Test
    public void testCheckEmailIsNotUnique() {
        //given
        var snapshot = createTestCommonUserSnapshot();

        doReturn(true)
                .when(userJpaRepository)
                .existsByUsername(snapshot.getUsername());

        doReturn(false)
                .when(userJpaRepository)
                .existsByEmail(snapshot.getEmail());

        //when
        var isNotUnique = userRepository.checkUserUniqueness(snapshot);

        //then
        assertFalse(isNotUnique);
    }

    @Test
    public void testCheckEmailAndUsernameAreNotUnique() {
        //given
        var snapshot = createTestCommonUserSnapshot();

        doReturn(false)
                .when(userJpaRepository)
                .existsByUsername(snapshot.getUsername());


        //when
        var isNotUnique = userRepository.checkUserUniqueness(snapshot);

        //then
        assertFalse(isNotUnique);
    }



    @Test
    public void testCheckUserIsUniqueThrowUsernameException() {

    }

    private void assertIsUserSnapshotValid(UserEntity userEntity, UserSnapshot snapshot) {
        assertEquals(createSnapshotFromEntity(userEntity), snapshot);
    }

    private void assertIsUserSnapshotValid(UserSnapshot snapshot, UserSnapshot savedSnapshot) {
        assertEquals(snapshot, savedSnapshot);
    }

    private UserSnapshot createSnapshotFromEntity(UserEntity user) {
        return new UserSnapshot(
                new UserId(UUID.fromString(user.getUserId())),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                UserRole.COMMON_USER,
                user.isEnabled()
        );
    }



    private ApplicationUserDomainException throwExceptionWhenFindByUsername(String username){
        return assertThrows(UserNotFoundException.class, () -> userRepository.findByUsername(username));
    }

    private void assertIsExceptionAUserNotFound(ApplicationUserDomainException exception) {
        assertEquals(new UserNotFoundException().getMessage(), exception.getMessage());
    }

    private UserSnapshot createTestCommonUserSnapshot() {
        return new UserSnapshot(new UserId(UUID.randomUUID()),
                "test",
                "email@email.to",
                "password",
                UserRole.COMMON_USER,
                false
                );
    }
}
