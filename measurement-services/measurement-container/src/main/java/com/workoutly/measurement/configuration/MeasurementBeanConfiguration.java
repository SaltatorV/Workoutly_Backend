package com.workoutly.measurement.configuration;

import com.workoutly.measurement.MeasurementDomainService;
import com.workoutly.measurement.MeasurementDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeasurementBeanConfiguration {

    @Bean
    public MeasurementDomainService measurementDomainService() {
        return new MeasurementDomainServiceImpl();
    }
}
