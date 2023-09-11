package com.workoutly.measurement.mapper;

import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.entity.BodyMeasurementEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeasurementDatabaseMapperTest {

    private MeasurementDatabaseMapper mapper = new MeasurementDatabaseMapper();

    @Test
    public void testMapBodyMeasurementSnapshotToEntity() {
        //given
        var snapshot = createSampleBodyMeasurementSnapshot();

        //when
        var entity = mapper.mapBodyMeasurementSnapshotToEntity(snapshot);

        //then
        assertEquals(mapBodyMeasurementSnapshotToEntity(snapshot), entity);
    }

    @Test
    public void testMapBodyMeasurementEntityToSnapshot() {
        //given
        var entity = createSampleBodyMeasurementEntity();

        //when
        var snapshot = mapper.mapBodyMeasurementEntityToSnapshot(entity);

        //then
        assertEquals(mapBodyMeasurementEntityToSnapshot(entity), snapshot);
    }

    @Test
    public void testMapBodyMeasurementListToSnapshots() {
        //given
        var entityList = List.of(createSampleBodyMeasurementEntity());

        //when
        var snapshotList = mapper.mapBodyMeasurementListToSnapshots(entityList);

        //then
        assertEquals(mapEntityListToSnapshots(entityList), snapshotList);
    }


    private BodyMeasurementSnapshot createSampleBodyMeasurementSnapshot() {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.randomUUID()),
                10,11,12,13,14,15,16,17,18,19,20,
                Date.from(Instant.now()), "test"
        );
    }


    private BodyMeasurementEntity createSampleBodyMeasurementEntity() {
        return BodyMeasurementEntity.builder()
                .bodyMeasurementId(UUID.randomUUID().toString())
                .neck(11)
                .chest(12)
                .leftBiceps(13)
                .rightBiceps(14)
                .leftForearm(15)
                .rightForearm(16)
                .waist(17)
                .leftThigh(18)
                .rightThigh(19)
                .leftCalf(20)
                .rightCalf(21)
                .username("test")
                .build();
    }

    private BodyMeasurementEntity mapBodyMeasurementSnapshotToEntity(BodyMeasurementSnapshot snapshot) {
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

    private BodyMeasurementSnapshot mapBodyMeasurementEntityToSnapshot(BodyMeasurementEntity entity) {
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

    private List<BodyMeasurementSnapshot> mapEntityListToSnapshots(List<BodyMeasurementEntity> entityList) {
        return entityList
                .stream()
                .map(this::mapBodyMeasurementEntityToSnapshot)
                .collect(Collectors.toList());
    }
}
