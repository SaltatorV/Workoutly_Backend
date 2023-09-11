package com.workoutly.measurement.adapter;

import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.entity.BodyMeasurementEntity;
import com.workoutly.measurement.mapper.MeasurementDatabaseMapper;
import com.workoutly.measurement.repository.BodyMeasurementJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementRepositoryImplTest {

    @Mock
    private BodyMeasurementJpaRepository bodyMeasurementJpaRepository;
    @Mock
    private MeasurementDatabaseMapper mapper;
    @InjectMocks
    private MeasurementRepositoryImpl repository;

    @Test
    public void testSaveBodyMeasurement() {
        //given
        var bodyMeasurementSnapshot = createSampleBodyMeasurementSnapshot();
        var entity = mapSnapshotToEntity(bodyMeasurementSnapshot);

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
    public void testExistsByDate() {
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
    public void testNotExistsByDate() {
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
        var entity = mapSnapshotToEntity(bodyMeasurementSnapshot);

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
        var entityList = mapSnapshotToEntity(snapshotList);

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
        var entityList = mapSnapshotToEntity(snapshotList);

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



    private BodyMeasurementSnapshot createSampleBodyMeasurementSnapshot() {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.randomUUID()),
                10,11,12,13,14,15,16,17,18,19,20,
                Date.from(Instant.now()), "test"
        );
    }

    private BodyMeasurementEntity mapSnapshotToEntity(BodyMeasurementSnapshot snapshot) {
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

    private List<BodyMeasurementEntity> mapSnapshotToEntity(List<BodyMeasurementSnapshot> snapshots) {
        List<BodyMeasurementEntity> entityList = new ArrayList();

        for(BodyMeasurementSnapshot snapshot: snapshots) {
            entityList.add(mapSnapshotToEntity(snapshot));
        }

        return entityList;
    }
}
