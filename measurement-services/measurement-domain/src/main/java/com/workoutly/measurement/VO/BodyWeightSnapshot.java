package com.workoutly.measurement.VO;

import java.util.Date;
import java.util.Objects;

public class BodyWeightSnapshot {
    private BodyWeightId bodyWeightId;
    private double weight;
    private double bodyFat;
    private Date date;
    private String username;

    public BodyWeightSnapshot(BodyWeightId bodyWeightId, double weight, double bodyFat, Date date, String username) {
        this.bodyWeightId = bodyWeightId;
        this.weight = weight;
        this.bodyFat = bodyFat;
        this.date = date;
        this.username = username;
    }

    public BodyWeightId getBodyWeightId() {
        return bodyWeightId;
    }

    public double getWeight() {
        return weight;
    }

    public double getBodyFat() {
        return bodyFat;
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
        BodyWeightSnapshot that = (BodyWeightSnapshot) o;
        return Double.compare(that.weight, weight) == 0 && Double.compare(that.bodyFat, bodyFat) == 0 && Objects.equals(bodyWeightId, that.bodyWeightId) && Objects.equals(date, that.date) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bodyWeightId, weight, bodyFat, date, username);
    }
}
