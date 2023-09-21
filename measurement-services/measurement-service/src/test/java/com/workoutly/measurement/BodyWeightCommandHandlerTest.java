package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyWeightId;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.auth.MeasurementAuthenticationProvider;
import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.dto.command.DeleteMeasurementCommand;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import com.workoutly.measurement.event.BodyWeightUpdatedEvent;
import com.workoutly.measurement.exception.MeasurementAlreadyExistsException;
import com.workoutly.measurement.exception.MeasurementDomainException;
import com.workoutly.measurement.exception.MeasurementNotExistsException;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import com.workoutly.measurement.port.output.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
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
        var createdEvent = createInitializedEvent(bodyWeight, username);

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(false)
                .when(repository)
                .checkBodyWeightExists(command.getDate(), username);

        doReturn(bodyWeight)
                .when(mapper)
                .mapBodyWeightCommandToBodyWeight(command);

        doReturn(createdEvent)
                .when(service)
                .initializeBodyWeight(bodyWeight, username);
        //when
        var event = handler.createBodyWeight(command);

        //then
        assertEquals(createdEvent, event);
        verify(repository, times(1))
                .saveBodyWeight(createdEvent.getSnapshot());
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
        var exception = assertThrows(MeasurementDomainException.class, () -> handler.createBodyWeight(command));

        //then
        assertEquals(new MeasurementAlreadyExistsException().getMessage(), exception.getMessage());
    }


    @Test
    public void testBodyWeightUpdate() {
        //given
        var username = "test";
        var date = Date.from(Instant.now());
        var command = createBodyWeightCommand(date);
        var bodyWeightFromCommand = createBodyWeightFrom(command);
        var snapshotFromDb = createBodyWeight(date, username);
        var createdEvent = createBodyWeightUpdatedEvent(snapshotFromDb, bodyWeightFromCommand.createSnapshot());

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(Optional.of(snapshotFromDb))
                .when(repository)
                .findBodyWeightSnapshot(date, username);

        doReturn(bodyWeightFromCommand)
                .when(mapper)
                .mapBodyWeightCommandToBodyWeight(command);

        doReturn(createdEvent)
                .when(service)
                .updateBodyWeight(BodyWeight.restore(snapshotFromDb), bodyWeightFromCommand.createSnapshot());


        //when
        var event = handler.updateBodyWeight(command);

        //then
        assertEquals(createdEvent, event);
        verify(repository, times(1))
                .saveBodyWeight(createdEvent.getSnapshot());
    }

    @Test
    public void testUpdateBodyWeightThrowException() {
        //given
        var username = "test";
        var date = Date.from(Instant.now());
        var command = createBodyWeightCommand(date);

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(Optional.empty())
                .when(repository)
                .findBodyWeightSnapshot(date, username);

        //when
        var exception = assertThrows(MeasurementDomainException.class, () -> handler.updateBodyWeight(command));

        //then
        assertEquals(new MeasurementNotExistsException().getMessage(), exception.getMessage());
    }

    @Test
    public void testDeleteBodyWeight() {
        //given
        var command = new DeleteMeasurementCommand(Date.from(Instant.now()));
        var username = "test";

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        //when
        handler.deleteBodyWeight(command);

        //then
        verify(repository, times(1))
                .deleteBodyWeightByDate(command.getDate(), username);
    }

    private BodyWeightUpdatedEvent createBodyWeightUpdatedEvent(BodyWeightSnapshot snapshotFromDb, BodyWeightSnapshot bodyWeightFromCommand) {
        BodyWeight bodyWeight = BodyWeight.restore(snapshotFromDb);
        bodyWeight.updateValues(bodyWeightFromCommand);

        return new BodyWeightUpdatedEvent(bodyWeight.createSnapshot());
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

    private BodyWeightSnapshot createBodyWeight(Date date, String username) {
        return BodyWeight
                .create()
                .id(new BodyWeightId(UUID.randomUUID()))
                .date(date)
                .weight(-1000)
                .bodyFat(-100)
                .username(username)
                .build()
                .createSnapshot();
    }

    private BodyWeightCreatedEvent createInitializedEvent(BodyWeight bodyWeight, String username) {
        bodyWeight.initialize(username);
        return new BodyWeightCreatedEvent(bodyWeight.createSnapshot());
    }
}
