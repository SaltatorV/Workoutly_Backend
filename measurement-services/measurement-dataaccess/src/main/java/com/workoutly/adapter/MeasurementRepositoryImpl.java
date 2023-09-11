package com.workoutly.adapter;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.port.output.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeasurementRepositoryImpl implements MeasurementRepository {
    @Override
    public void saveBodyMeasurement(BodyMeasurementSnapshot snapshot) {

    }

    @Override
    public boolean checkBodyMeasurementExists(Date date, String authenticatedUser) {
        return false;
    }

    @Override
    public Optional<BodyMeasurementSnapshot> findBodyMeasurementSnapshot(Date date, String authenticatedUser) {
        return Optional.empty();
    }

    @Override
    public void deleteBodyMeasurementByDate(Date date, String authenticatedUser) {

    }

    @Override
    public Optional<List<BodyMeasurementSnapshot>> findSummaryBodyMeasurements(String authenticatedUser) {
        return Optional.empty();
    }

    @Override
    public Optional<List<BodyMeasurementSnapshot>> findBodyMeasurementsByPage(int page, String authenticatedUser) {
        return Optional.empty();
    }
}
