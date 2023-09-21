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
@Table(name = "body_weight")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyWeightEntity {
    @Id
    @Column(name = "body_weight_id")
    private String bodyWeightId;
    private double weight;
    @Column(name = "body_fat")
    private double bodyFat;
    private Date date;
    private String username;
}
