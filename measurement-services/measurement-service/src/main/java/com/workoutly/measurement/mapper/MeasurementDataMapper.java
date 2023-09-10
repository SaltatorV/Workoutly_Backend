package com.workoutly.measurement.mapper;

import com.workoutly.measurement.BodyMeasurement;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.response.BodyMeasurementSummaryResponse;
import com.workoutly.measurement.dto.response.MessageResponse;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;

import java.util.List;

public class MeasurementDataMapper {
    public BodyMeasurement mapBodyMeasurementCommandToBodyMeasurement(BodyMeasurementCommand command) {
        return null;
    }

    public MessageResponse mapBodyMeasurementCreatedEventToResponse(BodyMeasurementCreatedEvent event) {
        return null;
    }

    public MessageResponse mapBodyMeasurementUpdatedEventToResponse(BodyMeasurementUpdatedEvent event) {
        return null;
    }

    public MessageResponse mapToBodyMeasurementDeletedMessage() {
        return null;
    }

    public BodyMeasurementSummaryResponse mapBodyMeasurementSnapshotsToResponse(List<BodyMeasurementSnapshot> snapshots) {
        return null;
    }
}
