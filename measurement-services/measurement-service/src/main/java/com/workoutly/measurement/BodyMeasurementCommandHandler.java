package com.workoutly.measurement;

import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.auth.AuthenticationProvider;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.BodyMeasurementDeleteCommand;
import com.workoutly.measurement.event.BodyMeasurementCreatedEvent;
import com.workoutly.measurement.event.BodyMeasurementUpdatedEvent;
import com.workoutly.measurement.exception.BodyMeasurementAlreadyExistsException;
import com.workoutly.measurement.exception.BodyMeasurementNotExistsException;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import com.workoutly.measurement.port.output.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class BodyMeasurementCommandHandler {
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

    @Transactional
    public BodyMeasurementUpdatedEvent updateBodyMeasurement(BodyMeasurementCommand command) {

        Optional<BodyMeasurementSnapshot> snapshotFromDb = repository.findBodyMeasurementSnapshot(command.getDate(), provider.getAuthenticatedUser());

        checkSnapshotExists(snapshotFromDb);

        BodyMeasurement measurementFromCommand = mapper.mapBodyMeasurementCommandToBodyMeasurement(command);
        BodyMeasurement measurementFromDb = BodyMeasurement.restore(snapshotFromDb.get());

        BodyMeasurementUpdatedEvent event = domainService.updateBodyMeasurement(measurementFromDb, measurementFromCommand.createSnapshot());

        repository.saveBodyMeasurement(event.getSnapshot());

        return event;
    }

    @Transactional
    public void deleteBodyMeasurement(BodyMeasurementDeleteCommand command) {
        repository.deleteBodyMeasurementByDate(command.getDate(), provider.getAuthenticatedUser());
    }

    @Transactional(readOnly = true)
    public List<BodyMeasurementSnapshot> getSummaryBodyMeasurements() {
        Optional<List<BodyMeasurementSnapshot>> bodyMeasurements =
                repository.findSummaryBodyMeasurements(provider.getAuthenticatedUser());

        if(bodyMeasurements.isEmpty()) {
            return List.of();
        }

        return bodyMeasurements.get();
    }

    @Transactional(readOnly = true)
    public List<BodyMeasurementSnapshot> getBodyMeasurementsPage() {
        return null;
    }


    private void checkMeasurementAlreadyExists(Date date, String authenticatedUser) {
        if(repository.checkBodyMeasurementExists(date, authenticatedUser)) {
            throw new BodyMeasurementAlreadyExistsException();
        }
    }

    private void checkSnapshotExists(Optional<BodyMeasurementSnapshot> snapshot) {
        if(snapshot.isEmpty()) {
            throw new BodyMeasurementNotExistsException();
        }
    }
}
