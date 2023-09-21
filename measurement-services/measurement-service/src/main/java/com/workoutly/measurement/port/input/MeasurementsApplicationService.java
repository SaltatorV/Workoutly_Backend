package com.workoutly.measurement.port.input;

import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.DeleteMeasurementCommand;
import com.workoutly.measurement.dto.query.BodyMeasurementsPageQuery;
import com.workoutly.measurement.dto.response.BodyMeasurementsResponse;
import com.workoutly.measurement.dto.response.MessageResponse;

public interface MeasurementsApplicationService {
    MessageResponse createBodyMeasurement(BodyMeasurementCommand command);

    MessageResponse updateBodyMeasurement(BodyMeasurementCommand command);

    MessageResponse deleteBodyMeasurement(DeleteMeasurementCommand command);

    BodyMeasurementsResponse findSummaryBodyMeasurements();

    BodyMeasurementsResponse findBodyMeasurements(BodyMeasurementsPageQuery query);
}
