package com.workoutly.measurement.repository;

import com.workoutly.measurement.entity.BodyWeightEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BodyWeightJpaRepository extends JpaRepository<BodyWeightEntity, String> {
    boolean existsByDateAndUsername(Date date, String username);
    Optional<BodyWeightEntity> findByDateAndUsername(Date date, String username);
    void deleteByDateAndUsername(Date date, String username);
    List<BodyWeightEntity> findFirst10ByUsernameOrderByDateDesc(String username);
    List<BodyWeightEntity> findByUsername(String username, Pageable pageable);
}
