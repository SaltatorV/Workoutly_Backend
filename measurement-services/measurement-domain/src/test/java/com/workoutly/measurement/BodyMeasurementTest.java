package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyMeasurementId;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BodyMeasurementTest {

    @Test
    public void testInitializeBodyMeasurement() {
        //given
        var bodyMeasurement = createNotInitialziedBodyMeasurement();
        var username = "test";

        //when
        bodyMeasurement.initialize(username);

        //then
        assertIsMeasurementInitialized(bodyMeasurement, username);
    }

    @Test
    public void testCreateSnapshot() {
        //given
        var snapshot = createSnapshot();
        var bodyMeasurement = createBodyMeasurementFromSnapshot(snapshot);

        //when
        var createdSnapshot = bodyMeasurement.createSnapshot();

        //then
        assertEquals(snapshot, createdSnapshot);
    }

    @Test
    public void testRestoreBodyMeasurement() {
        //given
        var snapshot = createSnapshot();
        var bodyMeasurement = createBodyMeasurementFromSnapshot(snapshot);

        //when
        var restored = BodyMeasurement.restore(snapshot);

        //then
        assertEquals(bodyMeasurement, restored);
    }

    @Test
    public void testUpdateValues() {
        //given
        var snapshot = createSnapshot();
        var bodyMeasurement = createInitialziedBodyMeasurement();

        //when
        bodyMeasurement.updateValues(snapshot);

        //then
        assertMeasurementIsUpdated(bodyMeasurement, snapshot);
    }

    private BodyMeasurement createNotInitialziedBodyMeasurement() {
        return BodyMeasurement.create()
                .neck(10)
                .chest(10)
                .leftForearm(10)
                .rightForearm(10)
                .leftBiceps(10)
                .rightBiceps(10)
                .waist(10)
                .leftThigh(10)
                .rightThigh(10)
                .leftCalf(10)
                .rightCalf(10)
                .date(Date.from(Instant.now()))
                .build();
    }

    private BodyMeasurement createInitialziedBodyMeasurement() {
        return BodyMeasurement.create()
                .id(new BodyMeasurementId(UUID.randomUUID()))
                .neck(10)
                .chest(10)
                .leftForearm(10)
                .rightForearm(10)
                .leftBiceps(10)
                .rightBiceps(10)
                .waist(10)
                .leftThigh(10)
                .rightThigh(10)
                .leftCalf(10)
                .rightCalf(10)
                .date(Date.from(Instant.now()))
                .username("test")
                .build();
    }

    private BodyMeasurementSnapshot createSnapshot() {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.randomUUID()),
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                Date.from(Instant.now()),
                "Test"
        );
    }

    private BodyMeasurement createBodyMeasurementFromSnapshot(BodyMeasurementSnapshot snapshot) {
        return BodyMeasurement.create()
                .id(snapshot.getBodyMeasurementsId())
                .neck(snapshot.getNeck())
                .chest(snapshot.getChest())
                .leftForearm(snapshot.getLeftForearm())
                .rightForearm(snapshot.getRightForearm())
                .leftBiceps(snapshot.getLeftBiceps())
                .rightBiceps(snapshot.getRightBiceps())
                .waist(snapshot.getWaist())
                .leftThigh(snapshot.getLeftThigh())
                .rightThigh(snapshot.getRightThigh())
                .leftCalf(snapshot.getLeftCalf())
                .rightCalf(snapshot.getRightCalf())
                .date(snapshot.getDate())
                .username(snapshot.getUsername())
                .build();
    }

    private void assertIsMeasurementInitialized(BodyMeasurement bodyMeasurement, String username) {
        assertEquals(username, bodyMeasurement.createSnapshot().getUsername());
        assertNotNull(bodyMeasurement.createSnapshot().getBodyMeasurementsId());
    }

    private void assertMeasurementIsUpdated(BodyMeasurement bodyMeasurement, BodyMeasurementSnapshot snapshot) {
        BodyMeasurementSnapshot createdSnapshot = bodyMeasurement.createSnapshot();

        assertEquals(snapshot.getNeck(), createdSnapshot.getNeck());
        assertEquals(snapshot.getChest(), createdSnapshot.getChest());
        assertEquals(snapshot.getLeftBiceps(), createdSnapshot.getLeftBiceps());
        assertEquals(snapshot.getRightBiceps(), createdSnapshot.getRightBiceps());
        assertEquals(snapshot.getLeftForearm(), createdSnapshot.getLeftForearm());
        assertEquals(snapshot.getRightForearm(), createdSnapshot.getRightForearm());
        assertEquals(snapshot.getWaist(), createdSnapshot.getWaist());
        assertEquals(snapshot.getLeftThigh(), createdSnapshot.getLeftThigh());
        assertEquals(snapshot.getRightThigh(), createdSnapshot.getRightThigh());
        assertEquals(snapshot.getLeftCalf(), createdSnapshot.getLeftCalf());
        assertEquals(snapshot.getRightCalf(), createdSnapshot.getRightCalf());
    }
}