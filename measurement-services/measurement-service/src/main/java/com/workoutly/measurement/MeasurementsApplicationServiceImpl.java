package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
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
import com.workoutly.measurement.port.input.MeasurementsApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class MeasurementsApplicationServiceImpl implements MeasurementsApplicationService {
    private final BodyMeasurementCommandHandler bodyMeasurementCommandHandler;
    private final BodyWeightCommandHandler bodyWeightCommandHandler;
    private final MeasurementDataMapper mapper;

    public MessageResponse createBodyMeasurement(BodyMeasurementCommand command) {
        BodyMeasurementCreatedEvent event = bodyMeasurementCommandHandler.createBodyMeasurement(command);

        return mapper.mapBodyMeasurementCreatedEventToResponse(event);
    }

    public MessageResponse updateBodyMeasurement(BodyMeasurementCommand command) {
        BodyMeasurementUpdatedEvent event = bodyMeasurementCommandHandler.updateBodyMeasurement(command);

        return mapper.mapBodyMeasurementUpdatedEventToResponse(event);
    }

    public MessageResponse deleteBodyMeasurement(DeleteMeasurementCommand command) {
        bodyMeasurementCommandHandler.deleteBodyMeasurement(command);

        return mapper.mapToBodyMeasurementDeletedMessage();
    }

    public BodyMeasurementsResponse findSummaryBodyMeasurements() {
        List<BodyMeasurementSnapshot> snapshots = bodyMeasurementCommandHandler.getSummaryBodyMeasurements();

        return mapper.mapBodyMeasurementSnapshotsToResponse(snapshots);
    }

    public BodyMeasurementsResponse findBodyMeasurements(MeasurementsPageQuery query) {
        List<BodyMeasurementSnapshot> snapshots = bodyMeasurementCommandHandler.getBodyMeasurementsPage(query);

        return mapper.mapBodyMeasurementSnapshotsToResponse(snapshots);
    }

    public MessageResponse createBodyWeight(BodyWeightCommand command) {
        BodyWeightCreatedEvent event = bodyWeightCommandHandler.createBodyWeight(command);

        return mapper.mapBodyWeightCreatedEventToResponse(event);
    }

    public MessageResponse updateBodyWeight(BodyWeightCommand command) {
        BodyWeightUpdatedEvent event = bodyWeightCommandHandler.updateBodyWeight(command);

        return mapper.mapBodyWeightUpdatedEventToResponse(event);
    }

    public MessageResponse deleteBodyWeight(DeleteMeasurementCommand command) {
        bodyWeightCommandHandler.deleteBodyWeight(command);

        return mapper.mapToBodyWeightDeletedMessage();
    }

    public BodyWeightsResponse findSummaryBodyWeights(){
        List<BodyWeightSnapshot> snapshots = bodyWeightCommandHandler.getSummaryBodyWeights();

        return mapper.mapBodyWeightSnapshotsToResponse(snapshots);
    }

    public BodyWeightsResponse findBodyWeights(MeasurementsPageQuery query) {
        List<BodyWeightSnapshot> snapshots = bodyWeightCommandHandler.getBodyWeightsPage(query);

        return mapper.mapBodyWeightSnapshotsToResponse(snapshots);
    }
}
