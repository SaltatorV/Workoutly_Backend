package com.workoutly.measurement.port.output;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;

import java.util.Date;

public interface MeasurementRepository {
    void saveBodyMeasurement(BodyMeasurementSnapshot snapshot);

    boolean checkBodyMeasurementExists(Date date, String authenticatedUser);
}
