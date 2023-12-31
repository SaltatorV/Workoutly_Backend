package com.workoutly.application.user.adapter;

import com.workoutly.application.user.VO.*;
import com.workoutly.application.user.entity.UserEntity;
import com.workoutly.application.user.entity.UserRoleEntity;
import com.workoutly.application.user.entity.VerificationTokenEntity;
import com.workoutly.application.user.exception.ApplicationUserDomainException;
import com.workoutly.application.user.exception.UserNotFoundException;
import com.workoutly.application.user.mapper.UserDatabaseMapper;
import com.workoutly.application.user.repository.UserJpaRepository;
import com.workoutly.application.user.repository.UserRoleJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
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
    private UserRoleJpaRepository userRoleJpaRepository;
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
                .token(createToken())
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
                .token(createToken())
                .build();

        var snapshot = createSnapshotFromEntity(entity);

        doReturn(entity)
                .when(mapper)
                .mapUserSnapshotToUserEntity(snapshot);

        doReturn(createCommonRole())
                .when(userRoleJpaRepository)
                .getRoleEntityByPermissionName(snapshot.getRole().getRoleName());

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
        assertVerificationTokenSetUserEntity(entity);
    }

    @Test
    public void testCheckUserNotExist() {
        //given
        var snapshot = createTestCommonUserSnapshot();

        doReturn(false)
                .when(userJpaRepository)
                .existsByUsername(snapshot.getUsername());

        doReturn(false)
                .when(userJpaRepository)
                .existsByEmail(snapshot.getEmail());

        //when
        var isUserExists = userRepository.checkUserExists(snapshot);

        //then
        assertFalse(isUserExists);
    }

    @Test
    public void testCheckUsernameExists() {
        //given
        var snapshot = createTestCommonUserSnapshot();

        doReturn(true)
                .when(userJpaRepository)
                .existsByUsername(snapshot.getUsername());


        //when
        var isUserExists = userRepository.checkUserExists(snapshot);

        //then
        assertTrue(isUserExists);
    }

    @Test
    public void testCheckEmailExists() {
        //given
        var snapshot = createTestCommonUserSnapshot();

        doReturn(false)
                .when(userJpaRepository)
                .existsByUsername(snapshot.getUsername());

        doReturn(true)
                .when(userJpaRepository)
                .existsByEmail(snapshot.getEmail());

        //when
        var isUserExists = userRepository.checkUserExists(snapshot);

        //then
        assertTrue(isUserExists);
    }

    @Test
    public void testCheckEmailAndUsernameExist() {
        //given
        var snapshot = createTestCommonUserSnapshot();

        doReturn(true)
                .when(userJpaRepository)
                .existsByUsername(snapshot.getUsername());


        //when
        var isUserExists = userRepository.checkUserExists(snapshot);

        //then
        assertTrue(isUserExists);
    }

    @Test
    public void testFindByVerificationToken() {
        //given
        var token = createToken();
        var entity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .username("test")
                .password("password")
                .email("example@example.to")
                .isEnabled(false)
                .token(token)
                .build();
        var snapshot = createSnapshotFromEntity(entity);

        doReturn(Optional.of(entity))
                .when(userJpaRepository)
                .findByTokenToken(token.getToken());

        doReturn(snapshot)
                .when(mapper)
                .mapUserEntityToUserSnapshot(entity);

        //when
        var foundSnapshot = userRepository.findByVerificationToken(token.getToken());

        //then
        assertIsUserSnapshotValid(entity, foundSnapshot.get());
    }

    @Test
    public void testFindByVerificationTokenIsEmpty() {
        //given
        var token = createToken();


        doReturn(Optional.empty())
                .when(userJpaRepository)
                .findByTokenToken(token.getToken());


        //when
        var foundSnapshot = userRepository.findByVerificationToken(token.getToken());

        //then
        assertTrue(foundSnapshot.isEmpty());
    }

    private void assertIsUserSnapshotValid(UserEntity userEntity, UserSnapshot snapshot) {
        assertEquals(createSnapshotFromEntity(userEntity), snapshot);
    }

    private void assertIsUserSnapshotValid(UserSnapshot snapshot, UserSnapshot savedSnapshot) {
        assertEquals(snapshot, savedSnapshot);
    }


    private void assertVerificationTokenSetUserEntity(UserEntity entity) {
        assertNotNull(entity.getToken().getUser());
    }

    private UserSnapshot createSnapshotFromEntity(UserEntity user) {
        return new UserSnapshot(
                new UserId(UUID.fromString(user.getUserId())),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                UserRole.COMMON_USER,
                user.isEnabled(),
                createTokenSnapshot(user.getToken())
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
                false,
                createTokenSnapshot(createToken())
                );
    }

    private UserRoleEntity createCommonRole() {
        return new UserRoleEntity(1L, "commonUser");
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
