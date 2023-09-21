package com.workoutly.measurement.port.input;

import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.dto.command.DeleteMeasurementCommand;
import com.workoutly.measurement.dto.query.MeasurementsPageQuery;
import com.workoutly.measurement.dto.response.BodyMeasurementsResponse;
import com.workoutly.measurement.dto.response.BodyWeightsResponse;
import com.workoutly.measurement.dto.response.MessageResponse;


public interface MeasurementsApplicationService {
    MessageResponse createBodyMeasurement(BodyMeasurementCommand command);

    MessageResponse updateBodyMeasurement(BodyMeasurementCommand command);

    MessageResponse deleteBodyMeasurement(DeleteMeasurementCommand command);

    BodyMeasurementsResponse findSummaryBodyMeasurements();

    BodyMeasurementsResponse findBodyMeasurements(MeasurementsPageQuery query);

    MessageResponse createBodyWeight(BodyWeightCommand command);

    MessageResponse updateBodyWeight(BodyWeightCommand command);

    MessageResponse deleteBodyWeight(DeleteMeasurementCommand command);

    BodyWeightsResponse findSummaryBodyWeights();

    BodyWeightsResponse findBodyWeights(MeasurementsPageQuery query);
}
