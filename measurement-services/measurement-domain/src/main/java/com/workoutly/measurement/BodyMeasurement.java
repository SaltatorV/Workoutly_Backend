package com.workoutly.measurement;

import com.workoutly.common.entity.AggregateRoot;
import com.workoutly.measurement.VO.BodyMeasurementsId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;


import java.util.Date;
import java.util.UUID;

public class BodyMeasurement extends AggregateRoot<BodyMeasurementsId> {
    private double neck;
    private double chest;
    private double leftBiceps;
    private double rightBiceps;
    private double leftForearm;
    private double rightForearm;
    private double waist;
    private double leftThigh;
    private double rightThigh;
    private double leftCalf;
    private double rightCalf;
    private Date date;
    private String username;

    public static BodyMeasurement restore(BodyMeasurementSnapshot snapshot) {
        return new BodyMeasurement(create()
                .id(snapshot.getBodyMeasurementsId())
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
                .username(snapshot.getUsername()));
    }

    public void initialize(String user) {
        this.username = user;
        this.setId(new BodyMeasurementsId(UUID.randomUUID()));
    }

    public BodyMeasurementSnapshot createSnapshot() {
        return new BodyMeasurementSnapshot(getId(), neck, chest, leftBiceps, rightBiceps, leftForearm, rightForearm, waist, leftThigh, rightThigh, leftCalf, rightCalf, date, username);
    }

    private BodyMeasurement(Builder builder) {
        setId(builder.id);
        neck = builder.neck;
        chest = builder.chest;
        leftBiceps = builder.leftBiceps;
        rightBiceps = builder.rightBiceps;
        leftForearm = builder.leftForearm;
        rightForearm = builder.rightForearm;
        waist = builder.waist;
        leftThigh = builder.leftThigh;
        rightThigh = builder.rightThigh;
        leftCalf = builder.leftCalf;
        rightCalf = builder.rightCalf;
        date = builder.date;
        username = builder.username;
    }

    public static Builder create() {
        return new Builder();
    }

    public static final class Builder {
        private BodyMeasurementsId id;
        private double neck;
        private double chest;
        private double leftBiceps;
        private double rightBiceps;
        private double leftForearm;
        private double rightForearm;
        private double waist;
        private double leftThigh;
        private double rightThigh;
        private double leftCalf;
        private double rightCalf;
        private Date date;
        private String username;

        private Builder() {
        }

        public Builder id(BodyMeasurementsId val) {
            id = val;
            return this;
        }

        public Builder neck(double val) {
            neck = val;
            return this;
        }

        public Builder chest(double val) {
            chest = val;
            return this;
        }

        public Builder leftBiceps(double val) {
            leftBiceps = val;
            return this;
        }

        public Builder rightBiceps(double val) {
            rightBiceps = val;
            return this;
        }

        public Builder leftForearm(double val) {
            leftForearm = val;
            return this;
        }

        public Builder rightForearm(double val) {
            rightForearm = val;
            return this;
        }

        public Builder waist(double val) {
            waist = val;
            return this;
        }

        public Builder leftThigh(double val) {
            leftThigh = val;
            return this;
        }

        public Builder rightThigh(double val) {
            rightThigh = val;
            return this;
        }

        public Builder leftCalf(double val) {
            leftCalf = val;
            return this;
        }

        public Builder rightCalf(double val) {
            rightCalf = val;
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

        public BodyMeasurement build() {
            return new BodyMeasurement(this);
        }
    }
}
