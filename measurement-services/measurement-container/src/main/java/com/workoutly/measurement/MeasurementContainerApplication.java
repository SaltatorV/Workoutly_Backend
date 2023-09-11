package com.workoutly.measurement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.workoutly.measurement"})
@EntityScan(basePackages = { "com.workoutly.measurement" })
@SpringBootApplication(scanBasePackages = {"com.workoutly.measurement"})
public class MeasurementContainerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeasurementContainerApplication.class, args);
    }
}
