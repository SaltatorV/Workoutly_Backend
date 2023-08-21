package com.workoutly.measurement;

import com.workoutly.common.entity.AggregateRoot;
import com.workoutly.measurement.VO.BodyWeightId;
import com.workoutly.measurement.VO.BodyWeightSnapshot;

import java.util.Date;
import java.util.UUID;

public class BodyWeight extends AggregateRoot<BodyWeightId> {
    private double weight;
    private double bodyFat;
    private Date date;
    private String username;

    private BodyWeight(Builder builder) {
        setId(builder.id);
        weight = builder.weight;
        bodyFat = builder.bodyFat;
        date = builder.date;
        username = builder.username;
    }

    public void initialize(String authenticatedUser) {
        setId(new BodyWeightId(UUID.randomUUID()));
        username = authenticatedUser;
    }

    public static BodyWeight restore(BodyWeightSnapshot userWeightSnapshot) {
        return create()
                .id(userWeightSnapshot.getBodyWeightId())
                .weight(userWeightSnapshot.getWeight())
                .bodyFat(userWeightSnapshot.getBodyFat())
                .username(userWeightSnapshot.getUsername())
                .date(userWeightSnapshot.getDate())
                .build();
    }

    public static Builder create() {
        return new Builder();
    }

    public BodyWeightSnapshot createSnapshot() {
        return new BodyWeightSnapshot(getId(), weight, bodyFat, date, username);
    }

    public static final class Builder {
        private BodyWeightId id;
        private double weight;
        private double bodyFat;
        private Date date;
        private String username;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder id(BodyWeightId val) {
            id = val;
            return this;
        }

        public Builder weight(double val) {
            weight = val;
            return this;
        }

        public Builder bodyFat(double val) {
            bodyFat = val;
            return this;
        }

        public Builder date(Date val) {
            date = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public BodyWeight build() {
            return new BodyWeight(this);
        }
    }
}
