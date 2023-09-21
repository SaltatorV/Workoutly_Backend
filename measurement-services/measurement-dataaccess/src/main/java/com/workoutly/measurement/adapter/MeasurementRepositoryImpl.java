package com.workoutly.measurement.adapter;

import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.entity.BodyMeasurementEntity;
import com.workoutly.measurement.entity.BodyWeightEntity;
import com.workoutly.measurement.mapper.MeasurementDatabaseMapper;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.port.output.MeasurementRepository;
import com.workoutly.measurement.repository.BodyMeasurementJpaRepository;
import com.workoutly.measurement.repository.BodyWeightJpaRepository;
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

    private final BodyMeasurementJpaRepository bodyMeasurementJpaRepository;
    private final BodyWeightJpaRepository bodyWeightJpaRepository;
    private final MeasurementDatabaseMapper mapper;

    @Override
    public void saveBodyMeasurement(BodyMeasurementSnapshot snapshot) {
        bodyMeasurementJpaRepository.save(mapper.mapBodyMeasurementSnapshotToEntity(snapshot));
    }

    @Override
    public boolean checkBodyMeasurementExists(Date date, String authenticatedUser) {
        return bodyMeasurementJpaRepository.existsByDateAndUsername(date, authenticatedUser);
    }

    @Override
    public Optional<BodyMeasurementSnapshot> findBodyMeasurementSnapshot(Date date, String authenticatedUser) {
        Optional<BodyMeasurementEntity> entity = bodyMeasurementJpaRepository.findByDateAndUsername(date, authenticatedUser);

        return entity.map(mapper::mapBodyMeasurementEntityToSnapshot);
    }

    @Override
    public void deleteBodyMeasurementByDate(Date date, String authenticatedUser) {
        bodyMeasurementJpaRepository.deleteByDateAndUsername(date, authenticatedUser);
    }

    @Override
    public List<BodyMeasurementSnapshot> findSummaryBodyMeasurements(String authenticatedUser) {
        return mapper.mapBodyMeasurementListToSnapshots(bodyMeasurementJpaRepository.findFirst10ByUsernameOrderByDateDesc(authenticatedUser));
    }

    @Override
    public List<BodyMeasurementSnapshot> findBodyMeasurementsByPage(int page, String authenticatedUser) {
        Pageable pageSortedByDate = PageRequest.of(page, PAGE_SIZE, Sort.by("date").descending());
        return mapper.mapBodyMeasurementListToSnapshots(bodyMeasurementJpaRepository.findByUsername(authenticatedUser, pageSortedByDate));
    }

    @Override
    public boolean checkBodyWeightExists(Date date, String username) {
        return bodyWeightJpaRepository.existsByDateAndUsername(date, username);
    }

    @Override
    public void saveBodyWeight(BodyWeightSnapshot snapshot) {
        bodyWeightJpaRepository.save(mapper.mapBodyWeightSnapshotToEntity(snapshot));
    }

    @Override
    public Optional<BodyWeightSnapshot> findBodyWeightSnapshot(Date date, String authenticatedUser) {
        Optional<BodyWeightEntity> entity = bodyWeightJpaRepository.findByDateAndUsername(date, authenticatedUser);

        return entity.map(mapper::mapBodyWeightEntityToSnapshot);
    }

    @Override
    public void deleteBodyWeightByDate(Date date, String authenticatedUser) {
        bodyWeightJpaRepository.deleteByDateAndUsername(date, authenticatedUser);
    }

    @Override
    public List<BodyWeightSnapshot> findSummaryBodyWeights(String authenticatedUser) {
        return mapper.mapBodyWeightListToSnapshots(bodyWeightJpaRepository.findFirst10ByUsernameOrderByDateDesc(authenticatedUser));
    }

    @Override
    public List<BodyWeightSnapshot> findBodyWeightsByPage(int page, String authenticatedUser) {
        Pageable pageSortedByDate = PageRequest.of(page, PAGE_SIZE, Sort.by("date").descending());
        return mapper.mapBodyWeightListToSnapshots(bodyWeightJpaRepository.findByUsername(authenticatedUser, pageSortedByDate));
    }
}
