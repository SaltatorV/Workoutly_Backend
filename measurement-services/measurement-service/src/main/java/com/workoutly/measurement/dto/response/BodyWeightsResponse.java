package com.workoutly.measurement.dto.response;

import com.workoutly.measurement.VO.BodyWeightSnapshot;
import lombok.Data;

import java.util.List;

@Data
public class BodyWeightsResponse {
    private final List<BodyWeightSnapshot> bodyWeights;
}
