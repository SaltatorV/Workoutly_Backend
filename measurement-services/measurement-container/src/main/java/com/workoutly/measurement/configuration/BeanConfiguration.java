package com.workoutly.measurement.configuration;

import com.workoutly.measurement.MeasurementDomainService;
import com.workoutly.measurement.MeasurementDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public MeasurementDomainService userDomainService() {
        return new MeasurementDomainServiceImpl();
    }
}
