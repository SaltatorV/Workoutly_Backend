package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyWeightId;
import com.workoutly.measurement.auth.MeasurementAuthenticationProvider;
import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import com.workoutly.measurement.exception.MeasurementAlreadyExistsException;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import com.workoutly.measurement.port.output.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BodyWeightCommandHandlerTest {

    @Mock
    private MeasurementDataMapper mapper;
    @Mock
    private MeasurementDomainService service;
    @Mock
    private MeasurementRepository repository;
    @Mock
    private MeasurementAuthenticationProvider provider;
    @InjectMocks
    private BodyWeightCommandHandler handler;

    @Test
    public void testCreateBodyWeight() {
        //given
        var command = createBodyWeightCommand();
        var username = "test";
        var bodyWeight = createBodyWeightFrom(command);
        var givenEvent = createInitializedEvent(bodyWeight, username);

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(false)
                .when(repository)
                .checkBodyWeightExists(command.getDate(), username);

        doReturn(bodyWeight)
                .when(mapper)
                .mapBodyWeightCommandToBodyWeight(command);

        doReturn(givenEvent)
                .when(service)
                .initializeBodyWeight(bodyWeight, username);
        //when
        var event = handler.createBodyWeight(command);

        //then
        assertEquals(givenEvent, event);
        verify(repository, times(1))
                .saveBodyWeight(givenEvent.getSnapshot());
    }

    @Test
    public void testCreateBodyWeightThrowException() {
        //given
        var command = createBodyWeightCommand();
        var username = "test";

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(true)
                .when(repository)
                .checkBodyWeightExists(command.getDate(), username);

        //when
        var exception = assertThrows(MeasurementAlreadyExistsException.class, () -> handler.createBodyWeight(command));

        //then
        assertEquals(new MeasurementAlreadyExistsException().getMessage(), exception.getMessage());
    }


    private BodyWeightCommand createBodyWeightCommand() {
        return createBodyWeightCommand(Date.from(Instant.now()));
    }

    private BodyWeightCommand createBodyWeightCommand(Date date) {
        return new BodyWeightCommand(10, 10, Date.from(Instant.now()));
    }

    private BodyWeight createBodyWeightFrom(BodyWeightCommand command) {
        return BodyWeight.create()
                .weight(command.getWeight())
                .bodyFat(command.getBodyFat())
                .date(command.getDate())
                .build();
    }

    private BodyWeight createBodyWeight(Date date, String username) {
        return BodyWeight
                .create()
                .id(new BodyWeightId(UUID.randomUUID()))
                .date(date)
                .weight(-1000)
                .bodyFat(-100)
                .username(username)
                .build();
    }

    private BodyWeightCreatedEvent createInitializedEvent(BodyWeight bodyWeight, String username) {
        bodyWeight.initialize(username);
        return new BodyWeightCreatedEvent(bodyWeight.createSnapshot());
    }
}
