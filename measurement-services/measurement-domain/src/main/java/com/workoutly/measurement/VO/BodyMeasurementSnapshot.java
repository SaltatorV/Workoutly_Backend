package com.workoutly.measurement.VO;

import java.util.Date;
import java.util.Objects;

public class BodyMeasurementSnapshot {
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

    public BodyMeasurementSnapshot(BodyMeasurementsId bodyMeasurementsId, double neck, double chest, double leftBiceps, double rightBiceps, double leftForearm, double rightForearm, double waist, double leftThigh, double rightThigh, double leftCalf, double rightCalf, Date date, String username) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyMeasurementSnapshot that = (BodyMeasurementSnapshot) o;
        return Double.compare(that.neck, neck) == 0 && Double.compare(that.chest, chest) == 0 && Double.compare(that.leftBiceps, leftBiceps) == 0 && Double.compare(that.rightBiceps, rightBiceps) == 0 && Double.compare(that.leftForearm, leftForearm) == 0 && Double.compare(that.rightForearm, rightForearm) == 0 && Double.compare(that.waist, waist) == 0 && Double.compare(that.leftThigh, leftThigh) == 0 && Double.compare(that.rightThigh, rightThigh) == 0 && Double.compare(that.leftCalf, leftCalf) == 0 && Double.compare(that.rightCalf, rightCalf) == 0 && Objects.equals(bodyMeasurementsId, that.bodyMeasurementsId) && Objects.equals(date, that.date) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bodyMeasurementsId, neck, chest, leftBiceps, rightBiceps, leftForearm, rightForearm, waist, leftThigh, rightThigh, leftCalf, rightCalf, date, username);
    }

    @Override
    public String toString() {
        return "BodyMeasurementSnapshot{" +
                "bodyMeasurementsId=" + bodyMeasurementsId +
                ", neck=" + neck +
                ", chest=" + chest +
                ", leftBiceps=" + leftBiceps +
                ", rightBiceps=" + rightBiceps +
                ", leftForearm=" + leftForearm +
                ", rightForearm=" + rightForearm +
                ", waist=" + waist +
                ", leftThigh=" + leftThigh +
                ", rightThigh=" + rightThigh +
                ", leftCalf=" + leftCalf +
                ", rightCalf=" + rightCalf +
                ", date=" + date +
                ", username='" + username + '\'' +
                '}';
    }
}
