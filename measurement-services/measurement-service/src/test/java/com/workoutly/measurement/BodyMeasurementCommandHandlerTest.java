package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.auth.AuthenticationProvider;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.BodyMeasurementDeleteCommand;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.exception.BodyMeasurementAlreadyExistsException;
import com.workoutly.measurement.exception.BodyMeasurementNotExistsException;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BodyMeasurementCommandHandlerTest {

    @Mock
    private MeasurementDataMapper mapper;
    @Mock
    private MeasurementDomainService service;
    @Mock
    private MeasurementRepository repository;
    @Mock
    private AuthenticationProvider provider;
    @InjectMocks
    private BodyMeasurementCommandHandler handler;

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

    @Test
    public void testUpdateBodyMeasurements() {
        //given
        var command = createSampleBodyMeasurementCommand();
        var measurementFromCommand = createBodyMeasurementFrom(command);
        var username = "test";
        var snapshotToUpdate = createSampleBodyMeasurementSnapshotFrom(username, command.getDate());
        var event = createBodyMeasurementUpdatedFrom(snapshotToUpdate, measurementFromCommand);

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(Optional.of(snapshotToUpdate))
                .when(repository)
                .findBodyMeasurementSnapshot(command.getDate(), username);

        doReturn(measurementFromCommand)
                .when(mapper)
                .mapBodyMeasurementCommandToBodyMeasurement(command);

        doReturn(event)
                .when(service)
                .updateBodyMeasurement(BodyMeasurement.restore(snapshotToUpdate), measurementFromCommand.createSnapshot());

        //when
        var returnedEvent = handler.updateBodyMeasurement(command);

        //then
        assertEquals(event, returnedEvent);
        verify(repository, times(1)).saveBodyMeasurement(returnedEvent.getSnapshot());

    }

    @Test
    public void testUpdateBodyMeasurementsThrowException() {
        var command = createSampleBodyMeasurementCommand();
        var username = "test";
        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(Optional.empty())
                .when(repository)
                .findBodyMeasurementSnapshot(command.getDate(), username);

        //when
        var exception = throwExceptionWhenBodyMeasurementNotExists(command);

        //then
        assertExceptionIsBodyMeasurementNotExists(exception);

    }

    @Test
    public void testDeleteBodyMeasurement() {
        //given
        var command = new BodyMeasurementDeleteCommand(Date.from(Instant.now()));
        var username = "test";

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        //when
        handler.deleteBodyMeasurement(command);

        //then
        verify(repository, times(1)).deleteBodyMeasurementByDate(command.getDate(), username);
    }

    @Test
    public void testGetSummaryBodyMeasurements() {
        //given
        var username = "test";
        var date = Date.from(Instant.now());
        var bodyMeasurementsList = List.of(createSampleBodyMeasurementSnapshotFrom(username, date));

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(Optional.of(bodyMeasurementsList))
                .when(repository)
                .findSummaryBodyMeasurements(username);

        //when
        var list = handler.getSummaryBodyMeasurements();

        //then
        assertEquals(bodyMeasurementsList, list);
    }

    private BodyMeasurementUpdatedEvent createBodyMeasurementUpdatedFrom(BodyMeasurementSnapshot snapshotToUpdate, BodyMeasurement measurementFromCommand) {
        BodyMeasurement measurementToUpdate = BodyMeasurement.restore(snapshotToUpdate);
        measurementToUpdate.updateValues(measurementToUpdate.createSnapshot());

        return new BodyMeasurementUpdatedEvent(measurementToUpdate.createSnapshot());
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

    private BodyMeasurementSnapshot createSampleBodyMeasurementSnapshotFrom(String username, Date date) {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.randomUUID()),
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                30,
                date,
                username
        );
    }


    private BodyMeasurementCreatedEvent createBodyMeasurementEventFrom(BodyMeasurement bodyMeasurement, String username) {
        bodyMeasurement.initialize(username);
        return new BodyMeasurementCreatedEvent(bodyMeasurement.createSnapshot());
    }

    private MeasurementDomainException throwExceptionWhenBodyMeasurementAlreadyExists(BodyMeasurementCommand command) {
        return assertThrows(BodyMeasurementAlreadyExistsException.class, () -> handler.createBodyMeasurement(command));
    }

    private MeasurementDomainException throwExceptionWhenBodyMeasurementNotExists(BodyMeasurementCommand command) {
        return assertThrows(BodyMeasurementNotExistsException.class, () -> handler.updateBodyMeasurement(command));
    }

    private void assertExceptionIsBodyMeasurementAlreadyExists(MeasurementDomainException exception) {
        assertExceptionsMessagesEqual(new BodyMeasurementAlreadyExistsException(), exception);
    }

    private void assertExceptionIsBodyMeasurementNotExists(MeasurementDomainException exception) {
        assertExceptionsMessagesEqual(new BodyMeasurementNotExistsException(), exception);
    }

    private void assertExceptionsMessagesEqual(MeasurementDomainException expected, MeasurementDomainException actual) {
        assertEquals(expected.getMessage(), actual.getMessage());
    }
}
