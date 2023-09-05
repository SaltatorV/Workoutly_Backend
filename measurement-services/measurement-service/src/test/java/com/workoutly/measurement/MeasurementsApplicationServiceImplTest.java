package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.response.MessageResponse;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MeasurementsApplicationServiceImplTest {
    @Mock
    private BodyMeasurementCommandHandler bodyMeasurementCommandHandler;
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
}
