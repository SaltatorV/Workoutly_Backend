package com.workoutly.measurement.mapper;

import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.entity.BodyMeasurementEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MeasurementDatabaseMapper {
    public BodyMeasurementEntity mapBodyMeasurementSnapshotToEntity(BodyMeasurementSnapshot snapshot) {
        return BodyMeasurementEntity.builder()
                .bodyMeasurementId(snapshot.getBodyMeasurementsId().getId())
                .neck(snapshot.getNeck())
                .chest(snapshot.getChest())
                .leftBiceps(snapshot.getLeftBiceps())
                .rightBiceps(snapshot.getRightBiceps())
                .leftForearm(snapshot.getLeftForearm())
                .rightForearm(snapshot.getRightForearm())
                .waist(snapshot.getWaist())
                .leftThigh(snapshot.getLeftThigh())
                .rightThigh(snapshot.getRightThigh())
                .leftCalf(snapshot.getLeftCalf())
                .rightCalf(snapshot.getRightCalf())
                .date(snapshot.getDate())
                .username(snapshot.getUsername())
                .build();
    }

    public BodyMeasurementSnapshot mapBodyMeasurementEntityToSnapshot(BodyMeasurementEntity entity) {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.fromString(entity.getBodyMeasurementId())),
                        entity.getNeck(),
                        entity.getChest(),
                        entity.getLeftBiceps(),
                        entity.getRightBiceps(),
                        entity.getLeftForearm(),
                        entity.getRightForearm(),
                        entity.getWaist(),
                        entity.getLeftThigh(),
                        entity.getRightThigh(),
                        entity.getLeftCalf(),
                        entity.getRightCalf(),
                        entity.getDate(),
                        entity.getUsername()
                        );
    }

    public List<BodyMeasurementSnapshot> mapBodyMeasurementListToSnapshots(List<BodyMeasurementEntity> entityList) {
        return entityList
                .stream()
                .map(this::mapBodyMeasurementEntityToSnapshot)
                .collect(Collectors.toList());
    }
}
