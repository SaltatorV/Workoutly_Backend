package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyWeightId;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BodyWeightTest {

    @Test
    public void testInitializeBodyWeight() {
        //given
        var bodyWeight = createNotInitializedBodyWeight();
        var username = "test";

        //when
        bodyWeight.initialize(username);

        //then
        assertIsBodyWeightInitialized(bodyWeight, username);
    }

    @Test
    public void testCreateSnapshot() {
        //given
        var snapshot = createSnapshot();
        var bodyWeight = createBodyWeightFromSnapshot(snapshot);

        //when
        var createdSnapshot = bodyWeight.createSnapshot();

        //then
        assertEquals(snapshot, createdSnapshot);
    }

    @Test
    public void testRestoreBodyWeight() {
        //given
        var snapshot = createSnapshot();
        var bodyWeight = createBodyWeightFromSnapshot(snapshot);

        //when
        var restored = BodyWeight.restore(snapshot);

        //then
        assertEquals(bodyWeight, restored);
    }

    @Test
    public void testUpdateValues() {
        //given
        var snapshot = createSnapshot();
        var bodyWeightToUpdate = BodyWeight
                .create()
                .weight(-100)
                .bodyFat(-100)
                .build();

        //when
        bodyWeightToUpdate.updateValues(snapshot);

        //then
        assertIsBodyWeightUpdated(bodyWeightToUpdate, snapshot);
    }

    private void assertIsBodyWeightUpdated(BodyWeight bodyWeight, BodyWeightSnapshot snapshot) {
        BodyWeightSnapshot snapshotToCompare = bodyWeight.createSnapshot();

        assertEquals(snapshot.getWeight(), snapshotToCompare.getWeight());
        assertEquals(snapshot.getBodyFat(), snapshotToCompare.getBodyFat());
    }

    private BodyWeight createNotInitializedBodyWeight() {
        return BodyWeight.create()
                .date(Date.from(Instant.now()))
                .weight(80)
                .bodyFat(15)
                .build();
    }


    private BodyWeight createBodyWeightFromSnapshot(BodyWeightSnapshot snapshot) {
        return BodyWeight.create()
                .id(snapshot.getBodyWeightId())
                .date(snapshot.getDate())
                .weight(snapshot.getWeight())
                .bodyFat(snapshot.getBodyFat())
                .username(snapshot.getUsername())
                .build();
    }

    private BodyWeightSnapshot createSnapshot() {
        return new BodyWeightSnapshot(
                new BodyWeightId(UUID.randomUUID()),
                80,
                15,
                Date.from(Instant.now()),
                "test"
        );
    }

    private void assertIsBodyWeightInitialized(BodyWeight bodyWeight, String username) {
        assertEquals(username, bodyWeight.createSnapshot().getUsername());
        assertNotNull(bodyWeight.createSnapshot().getBodyWeightId());
    }

}


