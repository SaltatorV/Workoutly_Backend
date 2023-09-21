package com.workoutly.measurement;

import com.workoutly.measurement.auth.MeasurementAuthenticationProvider;
import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.event.BodyWeightCreatedEvent;
import com.workoutly.measurement.mapper.MeasurementDataMapper;
import com.workoutly.measurement.port.output.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BodyWeightCommandHandlerTest {

    @Mock
    private MeasurementDataMapper mapper;
    @Mock
    private MeasurementDomainService service;
    @Mock
    private MeasurementRepository repository;
    @Mock
    private MeasurementAuthenticationProvider provider;
    @InjectMocks
    private BodyWeightCommandHandler handler;

    @Test
    public void testCreateBodyWeight() {
        //given
        var command = createBodyWeightCommand();
        var username = "test";
        var bodyWeight = createBodyWeightFrom(command);
        var givenEvent = createInitializedEvent(bodyWeight, username);

        doReturn(username)
                .when(provider)
                .getAuthenticatedUser();

        doReturn(false)
                .when(repository)
                .checkBodyWeightExists(command.getDate(), username);

        doReturn(bodyWeight)
                .when(mapper)
                .mapBodyWeightCommandToBodyWeight(command);

        doReturn(givenEvent)
                .when(service)
                .initializeBodyWeight(bodyWeight, username);
        //when
        var event = handler.createBodyWeight(command);

        //then
        assertEquals(givenEvent, event);
        verify(repository, times(1))
                .saveBodyWeight(givenEvent.getSnapshot());
    }


    private BodyWeightCommand createBodyWeightCommand() {
        return new BodyWeightCommand(10, 10, Date.from(Instant.now()));
    }

    private BodyWeight createBodyWeightFrom(BodyWeightCommand command) {
        return BodyWeight.create()
                .weight(command.getWeight())
                .bodyFat(command.getBodyFat())
                .date(command.getDate())
                .build();
    }

    private BodyWeightCreatedEvent createInitializedEvent(BodyWeight bodyWeight, String username) {
        bodyWeight.initialize(username);
        return new BodyWeightCreatedEvent(bodyWeight.createSnapshot());
    }
}
