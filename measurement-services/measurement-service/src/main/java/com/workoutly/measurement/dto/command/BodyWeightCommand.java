package com.workoutly.measurement.dto.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyWeightCommand {
    @PositiveOrZero
    private double weight;
    @PositiveOrZero
    private double bodyFat;
    @NotNull
    private Date date;
}
