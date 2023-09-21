package com.workoutly.measurement.api;

import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.dto.command.DeleteMeasurementCommand;
import com.workoutly.measurement.dto.query.MeasurementsPageQuery;
import com.workoutly.measurement.dto.response.BodyWeightsResponse;
import com.workoutly.measurement.dto.response.MessageResponse;
import com.workoutly.measurement.port.input.MeasurementsApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/measurements/weight")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class BodyWeightController {
    private final MeasurementsApplicationService service;

    @PostMapping("/create")
    public MessageResponse createBodyWeight(@RequestBody BodyWeightCommand command) {
        return service.createBodyWeight(command);
    }

    @PutMapping("/update")
    public MessageResponse updateBodyWeight(@RequestBody BodyWeightCommand command) {
        return service.updateBodyWeight(command);
    }

    @DeleteMapping("/delete")
    public MessageResponse deleteBodyWeight(@RequestBody DeleteMeasurementCommand command) {
        return service.deleteBodyWeight(command);
    }

    @GetMapping("/summary")
    public BodyWeightsResponse findSummaryBodyMeasurements() {
        return service.findSummaryBodyWeights();
    }

    @GetMapping
    public BodyWeightsResponse findBodyMeasurements(@RequestBody MeasurementsPageQuery query) {
        return service.findBodyWeights(query);
    }
}
