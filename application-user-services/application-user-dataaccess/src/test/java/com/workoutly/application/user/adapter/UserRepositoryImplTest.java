package com.workoutly.application.user.adapter;

import com.workoutly.application.user.VO.UserSnapshot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    public void testFindByUsername() {

        //given
        UserEntity entity = anUserEntity()
                .withUsername("Test")
                .withPassword("password")
                .withEmail("example@example.to")
                .isEnabled()
                .build();

        //when
        Optional<UserSnapshot> foundSnapshot = userRepository.findByUsername("Test");

        //then
        assertIsUserSnapshotValid(foundSnapshot, entity);
    }

    @Test
    public void testFindByUsernameIsEmpty() {

        //given

        //when
        Optional<UserSnapshot> foundSnapshot = userRepository.findByUsername("Test");

        //then
        assertIsSnapshotEmpty(foundSnapshot);
    }
}
