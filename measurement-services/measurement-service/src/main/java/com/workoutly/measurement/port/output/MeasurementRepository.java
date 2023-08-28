package com.workoutly.measurement.port.output;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MeasurementRepository {
    void saveBodyMeasurement(BodyMeasurementSnapshot snapshot);

    boolean checkBodyMeasurementExists(Date date, String authenticatedUser);

    Optional<BodyMeasurementSnapshot> findBodyMeasurementSnapshot(Date date, String authenticatedUser);

    void deleteBodyMeasurementByDate(Date date, String authenticatedUser);

    Optional<List<BodyMeasurementSnapshot>> findSummaryBodyMeasurements(String authenticatedUser);
}
