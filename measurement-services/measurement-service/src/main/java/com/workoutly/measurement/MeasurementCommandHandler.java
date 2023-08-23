package com.workoutly.measurement;

import com.workoutly.measurement.auth.AuthenticationProvider;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.exception.BodyMeasurementAlreadyExistsException;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import com.workoutly.measurement.port.output.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@RequiredArgsConstructor
class MeasurementCommandHandler {
    private final MeasurementDataMapper mapper;
    private final MeasurementDomainService domainService;
    private final MeasurementRepository repository;
    private final AuthenticationProvider provider;


    @Transactional
    public BodyMeasurementCreatedEvent createBodyMeasurement(BodyMeasurementCommand command) {

        checkMeasurementAlreadyExists(command.getDate(), provider.getAuthenticatedUser());

        BodyMeasurement measurement = mapper.mapBodyMeasurementCommandToBodyMeasurement(command);
        BodyMeasurementCreatedEvent event = domainService.initializeBodyMeasurement(measurement, provider.getAuthenticatedUser());

        repository.saveBodyMeasurement(event.getSnapshot());
        return event;
    }

    private void checkMeasurementAlreadyExists(Date date, String authenticatedUser) {
        if(repository.checkBodyMeasurementExists(date, authenticatedUser)) {
            throw new BodyMeasurementAlreadyExistsException();
        }
    }
}
