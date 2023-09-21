package com.workoutly.measurement.port.output;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyWeightSnapshot;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MeasurementRepository {
    void saveBodyMeasurement(BodyMeasurementSnapshot snapshot);

    boolean checkBodyMeasurementExists(Date date, String authenticatedUser);

    Optional<BodyMeasurementSnapshot> findBodyMeasurementSnapshot(Date date, String authenticatedUser);

    void deleteBodyMeasurementByDate(Date date, String authenticatedUser);

    List<BodyMeasurementSnapshot> findSummaryBodyMeasurements(String authenticatedUser);

    List<BodyMeasurementSnapshot> findBodyMeasurementsByPage(int page, String authenticatedUser);

    boolean checkBodyWeightExists(Date date, String username);

    void saveBodyWeight(BodyWeightSnapshot snapshot);

    Optional<BodyWeightSnapshot> findBodyWeightSnapshot(Date date, String authenticatedUser);

    void deleteBodyWeightByDate(Date date, String authenticatedUser);

    List<BodyWeightSnapshot> findSummaryBodyWeights(String authenticatedUser);
}
