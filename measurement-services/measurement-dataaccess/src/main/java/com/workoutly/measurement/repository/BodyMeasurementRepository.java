package com.workoutly.measurement.repository;

import com.workoutly.measurement.entity.BodyMeasurementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyMeasurementRepository  extends JpaRepository<BodyMeasurementsEntity, String> {

}
