package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyMeasurementsId;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BodyMeasurementTest {

    @Test
    public void testInitializeBodyMeasurement() {
        //given
        var bodyMeasurement = createSampleBodyMeasurement();
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
        var bodyMeasurement = createSampleBodyMeasurement(snapshot);

        //when
        var createdSnapshot = bodyMeasurement.createSnapshot();

        //then
        assertEquals(snapshot, createdSnapshot);
    }

    private BodyMeasurement createSampleBodyMeasurement() {
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

    private BodyMeasurementSnapshot createSnapshot() {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementsId(UUID.randomUUID()),
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

    private BodyMeasurement createSampleBodyMeasurement(BodyMeasurementSnapshot snapshot) {
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

}