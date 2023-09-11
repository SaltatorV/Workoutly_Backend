package com.workoutly.measurement.mapper;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.entity.BodyMeasurementEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MeasurementDatabaseMapper {
    public BodyMeasurementEntity mapBodyMeasurementSnapshotToEntity(BodyMeasurementSnapshot snapshot) {
        return null;
    }

    public Optional<BodyMeasurementSnapshot> mapBodyMeasurementEntityToSnapshot(Optional<BodyMeasurementEntity> snapshot) {
        return null;
    }

    public List<BodyMeasurementSnapshot> mapBodyMeasurementListToSnapshots(List<BodyMeasurementEntity> first10ByOrderByDateDesc) {
        return null;
    }
}
