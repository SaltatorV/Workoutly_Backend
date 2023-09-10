package com.workoutly.measurement.dto.response;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import lombok.Data;

import java.util.List;

@Data
public class BodyMeasurementsResponse {
    private final List<BodyMeasurementSnapshot> bodyMeasurements;
}
