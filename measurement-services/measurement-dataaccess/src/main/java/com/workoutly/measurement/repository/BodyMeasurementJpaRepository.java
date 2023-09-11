package com.workoutly.measurement.repository;

import com.workoutly.measurement.entity.BodyMeasurementEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BodyMeasurementJpaRepository extends JpaRepository<BodyMeasurementEntity, String> {
    boolean existsByDateAndUsername(Date date, String username);
    Optional<BodyMeasurementEntity> findByDateAndUsername(Date date, String username);
    void deleteByDateAndUsername(Date date, String username);
    List<BodyMeasurementEntity> findFirst10ByUsernameOrderByDateDesc(String username);
    List<BodyMeasurementEntity> findByUsername(String username, Pageable pageable);
}
