package com.workoutly.measurement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Table(name = "body_measurement")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyMeasurementsEntity {
    @Id
    @Column(name = "body_measurement_id")
    private String bodyMeasurementId;
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
}
