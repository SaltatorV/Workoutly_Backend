package com.workoutly.application.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = { "com.workoutly.application.user"})
@EntityScan(basePackages = { "com.workoutly.application.user" })
@SpringBootApplication(scanBasePackages = {"com.workoutly.application.user"})
public class ApplicationUserContainerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationUserContainerApplication.class, args);
    }
}