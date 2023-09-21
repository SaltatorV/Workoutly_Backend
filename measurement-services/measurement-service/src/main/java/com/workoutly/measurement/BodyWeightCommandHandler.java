package com.workoutly.measurement;

import com.workoutly.measurement.auth.MeasurementAuthenticationProvider;
import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import com.workoutly.measurement.exception.MeasurementAlreadyExistsException;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import com.workoutly.measurement.port.output.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Component
@RequiredArgsConstructor
public class BodyWeightCommandHandler {
    private final MeasurementDataMapper mapper;
    private final MeasurementDomainService domainService;
    private final MeasurementRepository repository;
    private final MeasurementAuthenticationProvider provider;

    @Transactional
    public BodyWeightCreatedEvent createBodyWeight(BodyWeightCommand command) {

        checkMeasurementAlreadyExists(command.getDate(), provider.getAuthenticatedUser());

        BodyWeight bodyWeight = mapper.mapBodyWeightCommandToBodyWeight(command);
        BodyWeightCreatedEvent event = domainService.initializeBodyWeight(bodyWeight, provider.getAuthenticatedUser());

        repository.saveBodyWeight(event.getSnapshot());

        return event;
    }

    private void checkMeasurementAlreadyExists(Date date, String authenticatedUser) {
        if(repository.checkBodyWeightExists(date, authenticatedUser)) {
            throw new MeasurementAlreadyExistsException();
        }
    }
}
