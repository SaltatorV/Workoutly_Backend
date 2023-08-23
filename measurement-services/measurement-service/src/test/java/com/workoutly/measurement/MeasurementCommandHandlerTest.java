package com.workoutly.measurement;

import com.workoutly.measurement.auth.AuthenticationProvider;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.exception.BodyMeasurementAlreadyExistsException;
import com.workoutly.measurement.exception.MeasurementDomainException;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import com.workoutly.measurement.port.output.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementCommandHandlerTest {

    @Mock
    private MeasurementDataMapper mapper;
    @Mock
    private MeasurementDomainService service;
    @Mock
    private MeasurementRepository repository;
    @Mock
    private AuthenticationProvider provider;
    @InjectMocks
    private MeasurementCommandHandler handler;

    @Test
    public void testCreateBodyMeasurement() {
        //given
        var command = createSampleBodyMeasurementCommand();
        var bodyMeasurement = createBodyMeasurementFrom(command);
        var username = "test";
        var createdEvent = createBodyMeasurementEventFrom(bodyMeasurement, username);

        doReturn(false)
                .when(repository)
                .checkBodyMeasurementExists(command.getDate(), username);

        doReturn(bodyMeasurement)
                .when(mapper)
                .mapBodyMeasurementCommandToBodyMeasurement(command);

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(createdEvent)
                .when(service)
                .initializeBodyMeasurement(bodyMeasurement, username);

        //when
        var event = handler.createBodyMeasurement(command);

        //then
        assertEquals(createdEvent, event);
        verify(repository, times(1)).saveBodyMeasurement(createdEvent.getSnapshot());
    }

    @Test
    public void testCreateBodyMeasurementThrowException() {
        //given
        var command = createSampleBodyMeasurementCommand();
        var username = "test";

        doReturn(true)
                .when(repository)
                .checkBodyMeasurementExists(command.getDate(), username);

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        //when
        var exception = throwExceptionWhenBodyMeasurementAlreadyExists(command);

        //then
        assertExceptionIsBodyMeasurementAlreadyExists(exception);
    }

    private BodyMeasurementCommand createSampleBodyMeasurementCommand() {
        return new BodyMeasurementCommand(
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                Date.from(Instant.now())
        );
    }

    private BodyMeasurement createBodyMeasurementFrom(BodyMeasurementCommand command) {
        return BodyMeasurement.create()
                .neck(command.getNeck())
                .chest(command.getChest())
                .leftBiceps(command.getLeftBiceps())
                .rightBiceps(command.getRightBiceps())
                .leftForearm(command.getLeftForearm())
                .rightForearm(command.getRightForearm())
                .waist(command.getWaist())
                .leftThigh(command.getLeftThigh())
                .rightThigh(command.getRightThigh())
                .leftCalf(command.getLeftCalf())
                .rightCalf(command.getRightCalf())
                .date(command.getDate())
                .build();
    }


    private BodyMeasurementCreatedEvent createBodyMeasurementEventFrom(BodyMeasurement bodyMeasurement, String username) {
        bodyMeasurement.initialize(username);
        return new BodyMeasurementCreatedEvent(bodyMeasurement.createSnapshot());
    }

    private MeasurementDomainException throwExceptionWhenBodyMeasurementAlreadyExists(BodyMeasurementCommand command) {
        return assertThrows(BodyMeasurementAlreadyExistsException.class, () -> handler.createBodyMeasurement(command));
    }

    private void assertExceptionIsBodyMeasurementAlreadyExists(MeasurementDomainException exception) {
        assertExceptionsMessagesEqual(new BodyMeasurementAlreadyExistsException(), exception);
    }

    private void assertExceptionsMessagesEqual(MeasurementDomainException expected, MeasurementDomainException actual) {
        assertEquals(expected.getMessage(), actual.getMessage());
    }
}
