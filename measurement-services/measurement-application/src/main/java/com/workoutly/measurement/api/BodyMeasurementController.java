package com.workoutly.measurement.api;

import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.BodyMeasurementDeleteCommand;
import com.workoutly.measurement.dto.query.BodyMeasurementsPageQuery;
import com.workoutly.measurement.dto.response.BodyMeasurementsResponse;
import com.workoutly.measurement.dto.response.MessageResponse;
import com.workoutly.measurement.port.input.MeasurementsApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/measurements/body")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class BodyMeasurementController {
    private final MeasurementsApplicationService service;

    @PostMapping("/create")
    public MessageResponse createBodyMeasurement(@RequestBody BodyMeasurementCommand command) {
        return service.createBodyMeasurement(command);
    }

    @PutMapping("/update")
    public MessageResponse updateBodyMeasurement(@RequestBody BodyMeasurementCommand command) {
        return service.updateBodyMeasurement(command);
    }

    @DeleteMapping("/delete")
    public MessageResponse deleteBodyMeasurement(@RequestBody BodyMeasurementDeleteCommand command) {
        return service.deleteBodyMeasurement(command);
    }

    @GetMapping("/summary")
    public BodyMeasurementsResponse findSummaryBodyMeasurements() {
        return service.findSummaryBodyMeasurements();
    }

    @GetMapping
    public BodyMeasurementsResponse findBodyMeasurements(@RequestBody BodyMeasurementsPageQuery query) {
        return service.findBodyMeasurements(query);
    }

}
