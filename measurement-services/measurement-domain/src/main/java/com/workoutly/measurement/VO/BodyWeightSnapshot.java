package com.workoutly.measurement.VO;

import java.util.Date;

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


    public BodyWeightSnapshot(double weight, double bodyFat, Date date) {
        this.weight = weight;
        this.bodyFat = bodyFat;
        this.date = date;
    }

    public BodyWeightId getUserWeightId() {
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
}
