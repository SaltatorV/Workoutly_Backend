package com.workoutly.measurement.adapter;

import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyWeightId;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.entity.BodyMeasurementEntity;
import com.workoutly.measurement.entity.BodyWeightEntity;
import com.workoutly.measurement.mapper.MeasurementDatabaseMapper;
import com.workoutly.measurement.repository.BodyMeasurementJpaRepository;
import com.workoutly.measurement.repository.BodyWeightJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementRepositoryImplTest {

    @Mock
    private BodyMeasurementJpaRepository bodyMeasurementJpaRepository;
    @Mock
    private BodyWeightJpaRepository bodyWeightJpaRepository;
    @Mock
    private MeasurementDatabaseMapper mapper;
    @InjectMocks
    private MeasurementRepositoryImpl repository;

    @Test
    public void testSaveBodyMeasurement() {
        //given
        var bodyMeasurementSnapshot = createSampleBodyMeasurementSnapshot();
        var entity = mapBodySnapshotToEntity(bodyMeasurementSnapshot);

        doReturn(entity)
                .when(mapper)
                .mapBodyMeasurementSnapshotToEntity(bodyMeasurementSnapshot);

        //when
        repository.saveBodyMeasurement(bodyMeasurementSnapshot);

        //then
        verify(bodyMeasurementJpaRepository, times(1))
                .save(entity);
    }

    @Test
    public void testBodyMeasurementExistsByDate() {
        //given
        var date = Date.from(Instant.now());
        var username = "test";

        doReturn(true)
                .when(bodyMeasurementJpaRepository)
                .existsByDateAndUsername(date, username);

        //when
        var result = repository.checkBodyMeasurementExists(date, username);

        //then
        assertTrue(result);
    }

    @Test
    public void testBodyMeasurementNotExistsByDate() {
        //given
        var date = Date.from(Instant.now());
        var username = "test";

        doReturn(false)
                .when(bodyMeasurementJpaRepository)
                .existsByDateAndUsername(date, username);

        //when
        var result = repository.checkBodyMeasurementExists(date, username);

        //then
        assertFalse(result);
    }

    @Test
    public void testFindBodyMeasurementSnapshot() {
        //given
        var date = Date.from(Instant.now());
        var username = "test";
        var bodyMeasurementSnapshot = createSampleBodyMeasurementSnapshot();
        var entity = mapBodySnapshotToEntity(bodyMeasurementSnapshot);

        doReturn(Optional.of(entity))
                .when(bodyMeasurementJpaRepository)
                .findByDateAndUsername(date, username);

        doReturn(bodyMeasurementSnapshot)
                .when(mapper)
                .mapBodyMeasurementEntityToSnapshot(entity);

        //when
        var result = repository.findBodyMeasurementSnapshot(date, username);

        //then
        assertEquals(Optional.of(bodyMeasurementSnapshot), result);
    }

    @Test
    public void testDeleteBodyMeasurement() {
        //given
        var date = Date.from(Instant.now());
        var username = "test";


        //when
        repository.deleteBodyMeasurementByDate(date, username);

        //then
        verify(bodyMeasurementJpaRepository, times(1))
                .deleteByDateAndUsername(date, username);
    }

    @Test
    public void testFindSummaryBodyMeasurements() {
        //given
        var username = "test";
        var snapshotList = List.of(createSampleBodyMeasurementSnapshot());
        var entityList = mapBodySnapshotToEntity(snapshotList);

        doReturn(entityList)
                .when(bodyMeasurementJpaRepository)
                .findFirst10ByUsernameOrderByDateDesc(username);

        doReturn(snapshotList)
                .when(mapper)
                .mapBodyMeasurementListToSnapshots(entityList);

        //when
        var result = repository.findSummaryBodyMeasurements(username);

        //then
        assertEquals(snapshotList, result);
    }

    @Test
    public void testFindBodyMeasurementsByPage() {
        var username = "test";
        var snapshotList = List.of(createSampleBodyMeasurementSnapshot());
        var entityList = mapBodySnapshotToEntity(snapshotList);

        doReturn(entityList)
                .when(bodyMeasurementJpaRepository)
                .findByUsername(eq(username), any());

        doReturn(snapshotList)
                .when(mapper)
                .mapBodyMeasurementListToSnapshots(entityList);

        //when
        var result = repository.findBodyMeasurementsByPage(0, username);

        //then
        assertEquals(snapshotList, result);
    }

    @Test
    public void testSaveBodyWeight() {
        //given
        var bodyWeightSnapshot = createSampleBodyWeightSnapshot();
        var entity = mapWeightSnapshotToEntity(bodyWeightSnapshot);

        doReturn(entity)
                .when(mapper)
                .mapBodyWeightSnapshotToEntity(bodyWeightSnapshot);

        //when
        repository.saveBodyWeight(bodyWeightSnapshot);

        //then
        verify(bodyWeightJpaRepository, times(1))
                .save(entity);
    }

    @Test
    public void testBodyWeightExistsByDate() {
        //given
        var date = Date.from(Instant.now());
        var username = "test";

        doReturn(true)
                .when(bodyWeightJpaRepository)
                .existsByDateAndUsername(date, username);

        //when
        var result = repository.checkBodyWeightExists(date, username);

        //then
        assertTrue(result);
    }

    @Test
    public void testBodyWeightNotExistsByDate() {
        //given
        var date = Date.from(Instant.now());
        var username = "test";

        doReturn(false)
                .when(bodyWeightJpaRepository)
                .existsByDateAndUsername(date, username);

        //when
        var result = repository.checkBodyWeightExists(date, username);

        //then
        assertFalse(result);
    }

    @Test
    public void testFindBodyWeightSnapshot() {
        //given
        var date = Date.from(Instant.now());
        var username = "test";
        var bodyWeightSnapshot = createSampleBodyWeightSnapshot();
        var entity = mapWeightSnapshotToEntity(bodyWeightSnapshot);

        doReturn(Optional.of(entity))
                .when(bodyWeightJpaRepository)
                .findByDateAndUsername(date, username);

        doReturn(bodyWeightSnapshot)
                .when(mapper)
                .mapBodyWeightEntityToSnapshot(entity);

        //when
        var result = repository.findBodyWeightSnapshot(date, username);

        //then
        assertEquals(Optional.of(bodyWeightSnapshot), result);
    }

    @Test
    public void testDeleteBodyWeight() {
        //given
        var date = Date.from(Instant.now());
        var username = "test";


        //when
        repository.deleteBodyWeightByDate(date, username);

        //then
        verify(bodyWeightJpaRepository, times(1))
                .deleteByDateAndUsername(date, username);
    }

    @Test
    public void testFindSummaryBodyWeights() {
        //given
        var username = "test";
        var snapshotList = List.of(createSampleBodyWeightSnapshot());
        var entityList = mapWeightSnapshotToEntity(snapshotList);

        doReturn(entityList)
                .when(bodyWeightJpaRepository)
                .findFirst10ByUsernameOrderByDateDesc(username);

        doReturn(snapshotList)
                .when(mapper)
                .mapBodyWeightListToSnapshots(entityList);

        //when
        var result = repository.findSummaryBodyWeights(username);

        //then
        assertEquals(snapshotList, result);
    }

    @Test
    public void testFindBodyWeightsByPage() {
        var username = "test";
        var snapshotList = List.of(createSampleBodyWeightSnapshot());
        var entityList = mapWeightSnapshotToEntity(snapshotList);

        doReturn(entityList)
                .when(bodyWeightJpaRepository)
                .findByUsername(eq(username), any());

        doReturn(snapshotList)
                .when(mapper)
                .mapBodyWeightListToSnapshots(entityList);

        //when
        var result = repository.findBodyWeightsByPage(0, username);

        //then
        assertEquals(snapshotList, result);
    }

    private BodyMeasurementSnapshot createSampleBodyMeasurementSnapshot() {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.randomUUID()),
                10,11,12,13,14,15,16,17,18,19,20,
                Date.from(Instant.now()), "test"
        );
    }

    private BodyMeasurementEntity mapBodySnapshotToEntity(BodyMeasurementSnapshot snapshot) {
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
                .username(snapshot.getUsername())
                .build();
    }

    private List<BodyMeasurementEntity> mapBodySnapshotToEntity(List<BodyMeasurementSnapshot> snapshots) {
        return snapshots
                .stream()
                .map(this::mapBodySnapshotToEntity)
                .collect(Collectors.toList());
    }

    private BodyWeightSnapshot createSampleBodyWeightSnapshot() {
        return new BodyWeightSnapshot(
                new BodyWeightId(UUID.randomUUID()),
                100,21,
                Date.from(Instant.now()), "test"
        );
    }

    private BodyWeightEntity mapWeightSnapshotToEntity(BodyWeightSnapshot snapshot) {
        return BodyWeightEntity.builder()
                .bodyWeightId(snapshot.getBodyWeightId().getId())
                .weight(snapshot.getWeight())
                .bodyFat(snapshot.getBodyFat())
                .username(snapshot.getUsername())
                .build();
    }

    private List<BodyWeightEntity> mapWeightSnapshotToEntity(List<BodyWeightSnapshot> snapshots) {
        return snapshots
                .stream()
                .map(this::mapWeightSnapshotToEntity)
                .collect(Collectors.toList());
    }
}
