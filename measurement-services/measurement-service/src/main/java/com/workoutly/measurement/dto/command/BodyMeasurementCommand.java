package com.workoutly.measurement.dto.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyMeasurementCommand {
    @PositiveOrZero
    private double neck;
    @PositiveOrZero
    private double chest;
    @PositiveOrZero
    private double leftBiceps;
    @PositiveOrZero
    private double rightBiceps;
    @PositiveOrZero
    private double leftForearm;
    @PositiveOrZero
    private double rightForearm;
    @PositiveOrZero
    private double waist;
    @PositiveOrZero
    private double leftThigh;
    @PositiveOrZero
    private double rightThigh;
    @PositiveOrZero
    private double leftCalf;
    @PositiveOrZero
    private double rightCalf;
    @NotNull
    private Date date;
}
