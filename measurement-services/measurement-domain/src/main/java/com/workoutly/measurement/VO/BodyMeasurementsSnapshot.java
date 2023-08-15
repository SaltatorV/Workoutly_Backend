package com.workoutly.measurement.VO;

import java.util.Date;

public class BodyMeasurementsSnapshot {
    private BodyMeasurementsId bodyMeasurementsId;
    private final double neck;
    private final double chest;
    private final double leftBiceps;
    private final double rightBiceps;
    private final double leftForearm;
    private final double rightForearm;
    private final double waist;
    private final double leftThigh;
    private final double rightThigh;
    private final double leftCalf;
    private final double rightCalf;
    private final Date date;
    private String username;

    public BodyMeasurementsSnapshot(BodyMeasurementsId bodyMeasurementsId, double neck, double chest, double leftBiceps, double rightBiceps, double leftForearm, double rightForearm, double waist, double leftThigh, double rightThigh, double leftCalf, double rightCalf, Date date, String username) {
        this.bodyMeasurementsId = bodyMeasurementsId;
        this.neck = neck;
        this.chest = chest;
        this.leftBiceps = leftBiceps;
        this.rightBiceps = rightBiceps;
        this.leftForearm = leftForearm;
        this.rightForearm = rightForearm;
        this.waist = waist;
        this.leftThigh = leftThigh;
        this.rightThigh = rightThigh;
        this.leftCalf = leftCalf;
        this.rightCalf = rightCalf;
        this.date = date;
        this.username = username;
    }

    public BodyMeasurementsId getBodyMeasurementsId() {
        return bodyMeasurementsId;
    }

    public double getNeck() {
        return neck;
    }

    public double getChest() {
        return chest;
    }

    public double getLeftBiceps() {
        return leftBiceps;
    }

    public double getRightBiceps() {
        return rightBiceps;
    }

    public double getLeftForearm() {
        return leftForearm;
    }

    public double getRightForearm() {
        return rightForearm;
    }

    public double getWaist() {
        return waist;
    }

    public double getLeftThigh() {
        return leftThigh;
    }

    public double getRightThigh() {
        return rightThigh;
    }

    public double getLeftCalf() {
        return leftCalf;
    }

    public double getRightCalf() {
        return rightCalf;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }
}
