package com.workoutly.measurement.mapper;

import com.workoutly.measurement.BodyMeasurement;
import com.workoutly.measurement.BodyWeight;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.dto.response.BodyMeasurementsResponse;
import com.workoutly.measurement.dto.response.BodyWeightsResponse;
import com.workoutly.measurement.dto.response.MessageResponse;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import com.workoutly.measurement.event.BodyWeightUpdatedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeasurementDataMapper {
    public BodyMeasurement mapBodyMeasurementCommandToBodyMeasurement(BodyMeasurementCommand command) {
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

    public MessageResponse mapBodyMeasurementCreatedEventToResponse(BodyMeasurementCreatedEvent event) {
        String template = String.format("Body measurement for date %s has been created.", event.getSnapshot().getDate());
        return new MessageResponse(template);
    }

    public MessageResponse mapBodyMeasurementUpdatedEventToResponse(BodyMeasurementUpdatedEvent event) {
        String template = String.format("Body measurement for date %s has been updated.", event.getSnapshot().getDate());
        return new MessageResponse(template);
    }

    public MessageResponse mapToBodyMeasurementDeletedMessage() {
        return new MessageResponse("Body measurement has been deleted.");
    }

    public BodyMeasurementsResponse mapBodyMeasurementSnapshotsToResponse(List<BodyMeasurementSnapshot> snapshots) {
        return new BodyMeasurementsResponse(snapshots);
    }

    public BodyWeight mapBodyWeightCommandToBodyWeight(BodyWeightCommand command) {
        return BodyWeight.create()
                .weight(command.getWeight())
                .bodyFat(command.getBodyFat())
                .date(command.getDate())
                .build();
    }

    public MessageResponse mapBodyWeightCreatedEventToResponse(BodyWeightCreatedEvent event) {
        String template = String.format("Body weight for date %s has been created.", event.getSnapshot().getDate());
        return new MessageResponse(template);
    }

    public MessageResponse mapBodyWeightUpdatedEventToResponse(BodyWeightUpdatedEvent event) {
        String template = String.format("Body weight for date %s has been updated.", event.getSnapshot().getDate());
        return new MessageResponse(template);
    }

    public MessageResponse mapToBodyWeightDeletedMessage() {
        return new MessageResponse("Body weight has been deleted.");
    }

    public BodyWeightsResponse mapBodyWeightSnapshotsToResponse(List<BodyWeightSnapshot> snapshots) {
        return new BodyWeightsResponse(snapshots);
    }
}
