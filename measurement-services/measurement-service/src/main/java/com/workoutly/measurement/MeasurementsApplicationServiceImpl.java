package com.workoutly.measurement;

import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.BodyMeasurementDeleteCommand;
import com.workoutly.measurement.dto.response.MessageResponse;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import com.workoutly.measurement.port.input.MeasurementsApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MeasurementsApplicationServiceImpl implements MeasurementsApplicationService {
    private final BodyMeasurementCommandHandler bodyMeasurementCommandHandler;
    private final MeasurementDataMapper mapper;

    public MessageResponse createBodyMeasurement(BodyMeasurementCommand command) {
        BodyMeasurementCreatedEvent event = bodyMeasurementCommandHandler.createBodyMeasurement(command);

        return mapper.mapBodyMeasurementCreatedEventToResponse(event);
    }

    public MessageResponse updateBodyMeasurement(BodyMeasurementCommand command) {
        BodyMeasurementUpdatedEvent event = bodyMeasurementCommandHandler.updateBodyMeasurement(command);

        return mapper.mapBodyMeasurementUpdatedEventToResponse(event);
    }

    public MessageResponse deleteBodyMeasurement(BodyMeasurementDeleteCommand command) {
        bodyMeasurementCommandHandler.deleteBodyMeasurement(command);

        return mapper.mapToBodyMeasurementDeletedMessage();
    }
}
