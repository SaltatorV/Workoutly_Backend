package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyWeightId;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.dto.command.DeleteMeasurementCommand;
import com.workoutly.measurement.dto.query.MeasurementsPageQuery;
import com.workoutly.measurement.dto.response.BodyMeasurementsResponse;
import com.workoutly.measurement.dto.response.BodyWeightsResponse;
import com.workoutly.measurement.dto.response.MessageResponse;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import com.workoutly.measurement.event.BodyWeightUpdatedEvent;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementsApplicationServiceImplTest {
    @Mock
    private BodyMeasurementCommandHandler bodyMeasurementCommandHandler;
    @Mock
    private BodyWeightCommandHandler bodyWeightCommandHandler;
    @Mock
    private MeasurementDataMapper mapper;
    @InjectMocks
    private MeasurementsApplicationServiceImpl service;

    @Test
    public void testCreateBodyMeasurement() {
        //given
        var command = createSampleBodyMeasurementCommand();
        var event = createBodyMeasurementCreatedEvent(command);
        var message = new MessageResponse("Body measurement created");

        doReturn(event)
                .when(bodyMeasurementCommandHandler)
                .createBodyMeasurement(command);

        doReturn(message)
                .when(mapper)
                .mapBodyMeasurementCreatedEventToResponse(event);


        //when
        var response = service.createBodyMeasurement(command);

        //then
        assertEquals(message, response);
    }

    @Test
    public void testUpdateBodyMeasurement() {
        //given
        var command = createSampleBodyMeasurementCommand();
        var event = createBodyMeasurementUpdatedEvent(command);
        var message = new MessageResponse("Body measurement updated");

        doReturn(event)
                .when(bodyMeasurementCommandHandler)
                .updateBodyMeasurement(command);

        doReturn(message)
                .when(mapper)
                .mapBodyMeasurementUpdatedEventToResponse(event);

        //when
        var response = service.updateBodyMeasurement(command);

        //then
        assertEquals(message, response);
    }

    @Test
    public void testDeleteBodyMeasurement() {
        //given
        var command = createSampleMeasurementDeleteCommand();
        var message = new MessageResponse("Body measurement deleted");

        doReturn(message)
                .when(mapper)
                .mapToBodyMeasurementDeletedMessage();

        //when
        var response = service.deleteBodyMeasurement(command);

        //then
        verify(bodyMeasurementCommandHandler, times(1))
                .deleteBodyMeasurement(command);

        assertEquals(message, response);

    }

    @Test
    public void testFindSummaryBodyMeasurements() {
        //given
        var snapshots = List.of(createSampleBodyMeasurementSnapshot());
        var message = new BodyMeasurementsResponse(snapshots);

        doReturn(snapshots)
                .when(bodyMeasurementCommandHandler)
                .getSummaryBodyMeasurements();

        doReturn(message)
                .when(mapper)
                .mapBodyMeasurementSnapshotsToResponse(snapshots);

        //when
        var response = service.findSummaryBodyMeasurements();

        //then
        assertEquals(message, response);

    }

    @Test
    public void testFindBodyMeasurementsByPage() {
        //given
        var query = new MeasurementsPageQuery(1);
        var snapshots = List.of(createSampleBodyMeasurementSnapshot());
        var message = new BodyMeasurementsResponse(snapshots);

        doReturn(snapshots)
                .when(bodyMeasurementCommandHandler)
                .getBodyMeasurementsPage(query);

        doReturn(message)
                .when(mapper)
                .mapBodyMeasurementSnapshotsToResponse(snapshots);

        //when
        var response = service.findBodyMeasurements(query);

        //then
        assertEquals(message, response);
    }

    @Test
    public void testCreateBodyWeight() {
        //given
        var command = createSampleBodyWeightCommand();
        var event = createBodyWeightCreatedEvent(command);
        var message = new MessageResponse("Body weight created");

        doReturn(event)
                .when(bodyWeightCommandHandler)
                .createBodyWeight(command);

        doReturn(message)
                .when(mapper)
                .mapBodyWeightCreatedEventToResponse(event);


        //when
        var response = service.createBodyWeight(command);

        //then
        assertEquals(message, response);
    }

    @Test
    public void testUpdateBodyWeight() {
        //given
        var command = createSampleBodyWeightCommand();
        var event = createBodyWeightUpdatedEvent(command);
        var message = new MessageResponse("Body weight updated");

        doReturn(event)
                .when(bodyWeightCommandHandler)
                .updateBodyWeight(command);

        doReturn(message)
                .when(mapper)
                .mapBodyWeightUpdatedEventToResponse(event);

        //when
        var response = service.updateBodyWeight(command);

        //then
        assertEquals(message, response);
    }

    @Test
    public void testDeleteBodyWeight() {
        //given
        var command = createSampleMeasurementDeleteCommand();
        var message = new MessageResponse("Body weight deleted");

        doReturn(message)
                .when(mapper)
                .mapToBodyWeightDeletedMessage();

        //when
        var response = service.deleteBodyWeight(command);

        //then
        verify(bodyWeightCommandHandler, times(1))
                .deleteBodyWeight(command);

        assertEquals(message, response);
    }

    @Test
    public void testFindSummaryBodyWeights() {
        //given
        var snapshots = List.of(createSampleBodyWeightSnapshot());
        var message = new BodyWeightsResponse(snapshots);

        doReturn(snapshots)
                .when(bodyWeightCommandHandler)
                .getSummaryBodyWeights();

        doReturn(message)
                .when(mapper)
                .mapBodyWeightSnapshotsToResponse(snapshots);

        //when
        var response = service.findSummaryBodyWeights();

        //then
        assertEquals(message, response);
    }

    @Test
    public void testFindBodyWeightsByPage() {
        //given
        var query = new MeasurementsPageQuery(1);
        var snapshots = List.of(createSampleBodyWeightSnapshot());
        var message = new BodyWeightsResponse(snapshots);

        doReturn(snapshots)
                .when(bodyWeightCommandHandler)
                .getBodyWeightsPage(query);

        doReturn(message)
                .when(mapper)
                .mapBodyWeightSnapshotsToResponse(snapshots);

        //when
        var response = service.findBodyWeights(query);

        //then
        assertEquals(message, response);
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

    private BodyMeasurementCreatedEvent createBodyMeasurementCreatedEvent(BodyMeasurementCommand command) {
        BodyMeasurementSnapshot snapshot = createBodyMeasurementSnapshot(command);
        return new BodyMeasurementCreatedEvent(snapshot);
    }

    private BodyMeasurementUpdatedEvent createBodyMeasurementUpdatedEvent(BodyMeasurementCommand command) {
        BodyMeasurementSnapshot snapshot = createBodyMeasurementSnapshot(command);
        return new BodyMeasurementUpdatedEvent(snapshot);
    }

    private BodyMeasurementSnapshot createBodyMeasurementSnapshot(BodyMeasurementCommand command) {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.randomUUID()),
                command.getNeck(),
                command.getChest(),
                command.getLeftBiceps(),
                command.getRightBiceps(),
                command.getLeftForearm(),
                command.getRightForearm(),
                command.getWaist(),
                command.getLeftThigh(),
                command.getRightThigh(),
                command.getLeftCalf(),
                command.getRightCalf(),
                command.getDate(),
                "test"
        );
    }

    private BodyMeasurementSnapshot createSampleBodyMeasurementSnapshot() {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.randomUUID()),
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
                Date.from(Instant.now()),
                "test"
        );
    }

    private DeleteMeasurementCommand createSampleMeasurementDeleteCommand() {
        return new DeleteMeasurementCommand(Date.from(Instant.now()));
    }


    private BodyWeightCommand createSampleBodyWeightCommand() {
        return new BodyWeightCommand(100,20, Date.from(Instant.now()));
    }

    private BodyWeightCreatedEvent createBodyWeightCreatedEvent(BodyWeightCommand command) {
        BodyWeightSnapshot snapshot = createBodyWeightSnapshot(command);
        return new BodyWeightCreatedEvent(snapshot);
    }

    private BodyWeightUpdatedEvent createBodyWeightUpdatedEvent(BodyWeightCommand command) {
        BodyWeightSnapshot snapshot = createBodyWeightSnapshot(command);
        return new BodyWeightUpdatedEvent(snapshot);
    }

    private BodyWeightSnapshot createBodyWeightSnapshot(BodyWeightCommand command) {
        return new BodyWeightSnapshot(
                new BodyWeightId(UUID.randomUUID()),
                command.getWeight(),
                command.getBodyFat(),
                command.getDate(),
                "test"
        );
    }

    private BodyWeightSnapshot createSampleBodyWeightSnapshot() {
        return new BodyWeightSnapshot(
                new BodyWeightId(UUID.randomUUID()),
                100,
                20,
                Date.from(Instant.now()),
                "test"
        );
    }
}
