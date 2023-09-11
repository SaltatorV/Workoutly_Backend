package com.workoutly.measurement.adapter;

import com.workoutly.measurement.mapper.MeasurementDatabaseMapper;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.port.output.MeasurementRepository;
import com.workoutly.measurement.repository.BodyMeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeasurementRepositoryImpl implements MeasurementRepository {

    private final static int PAGE_SIZE = 10;

    private final BodyMeasurementRepository repository;
    private final MeasurementDatabaseMapper mapper;

    @Override
    public void saveBodyMeasurement(BodyMeasurementSnapshot snapshot) {
        repository.save(mapper.mapBodyMeasurementSnapshotToEntity(snapshot));
    }

    @Override
    public boolean checkBodyMeasurementExists(Date date, String authenticatedUser) {
        return repository.existsByDateAndUsername(date, authenticatedUser);
    }

    @Override
    public Optional<BodyMeasurementSnapshot> findBodyMeasurementSnapshot(Date date, String authenticatedUser) {
        return mapper.mapBodyMeasurementEntityToSnapshot(repository.findByDateAndUsername(date, authenticatedUser));
    }

    @Override
    public void deleteBodyMeasurementByDate(Date date, String authenticatedUser) {
        repository.deleteByDateAndUsername(date, authenticatedUser);
    }

    @Override
    public List<BodyMeasurementSnapshot> findSummaryBodyMeasurements(String authenticatedUser) {
        return mapper.mapBodyMeasurementListToSnapshots(repository.findFirst10ByUsernameOrderByDateDesc(authenticatedUser));
    }

    @Override
    public List<BodyMeasurementSnapshot> findBodyMeasurementsByPage(int page, String authenticatedUser) {
        Pageable pageSortedByDate = PageRequest.of(page, PAGE_SIZE, Sort.by("date").descending());
        return mapper.mapBodyMeasurementListToSnapshots(repository.findByUsername(authenticatedUser, pageSortedByDate));
    }
}
