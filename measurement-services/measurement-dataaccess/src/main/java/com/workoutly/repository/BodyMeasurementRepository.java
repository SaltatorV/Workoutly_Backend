package com.workoutly.repository;

import com.workoutly.entity.BodyMeasurementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyMeasurementRepository  extends JpaRepository<BodyMeasurementsEntity, String> {

}
