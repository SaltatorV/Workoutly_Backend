package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MeasurementDomainServiceImplTest {

    private MeasurementDomainService domainService = new MeasurementDomainServiceImpl();

    @Test
    public void testBodyWeightInitialize() {
        //given
        var bodyWeight = createNotInitializedBodyWeight();
        var username = "test";

        //when
        var event = domainService.initializeBodyWeight(bodyWeight, username);

        //then
        assertIsEventInitialized(event, username);
    }

    @Test
    public void testBodyMeasurementInitialize() {
        //given
        var bodyMeasurement = createNotInitialziedBodyMeasurement();
        var username = "test";

        //when
        var event = domainService.initializeBodyMeasurement(bodyMeasurement, username);

        //then
        assertIsEventInitialized(event, username);
    }

    @Test
    public void testUpdateBodyMeasurement() {
        //given
        var bodyMeasurement = createInitialziedBodyMeasurement();
        var snapshot = createBodyMeasurementSnapshot(bodyMeasurement.createSnapshot());

        //when
        var event = domainService.updateBodyMeasurement(bodyMeasurement, snapshot);

        //then
        assertEquals(snapshot, event.getSnapshot());
    }

    private BodyWeight createNotInitializedBodyWeight() {
        return BodyWeight.create()
                .date(Date.from(Instant.now()))
                .weight(80)
                .bodyFat(15)
                .build();
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

    private BodyMeasurementSnapshot createBodyMeasurementSnapshot(BodyMeasurementSnapshot snapshot) {
        return new BodyMeasurementSnapshot(
                snapshot.getBodyMeasurementsId(),
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                30,
                snapshot.getDate(),
                snapshot.getUsername()
        );
    }

    private void assertIsEventInitialized(BodyWeightCreatedEvent event, String username) {
        BodyWeightSnapshot snapshot = event.getSnapshot();

        assertEquals(username, snapshot.getUsername());
        assertNotNull(snapshot.getBodyWeightId());
    }

    private void assertIsEventInitialized(BodyMeasurementCreatedEvent event, String username) {
        BodyMeasurementSnapshot snapshot = event.getSnapshot();

        assertEquals(username, snapshot.getUsername());
        assertNotNull(snapshot.getBodyMeasurementsId());
    }
}
