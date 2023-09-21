package com.workoutly.measurement.api;

import com.workoutly.common.exception.ErrorResponse;
import com.workoutly.measurement.VO.BodyMeasurementId;
import com.workoutly.measurement.VO.BodyMeasurementSnapshot;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.DeleteMeasurementCommand;
import com.workoutly.measurement.dto.query.MeasurementsPageQuery;
import com.workoutly.measurement.dto.response.BodyMeasurementsResponse;
import com.workoutly.measurement.dto.response.MessageResponse;
import com.workoutly.measurement.mock.MockExceptionHandler;
import com.workoutly.measurement.port.input.MeasurementsApplicationService;
import jakarta.validation.ValidationException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.workoutly.measurement.mock.MockExceptionHandler.createErrorResponse;
import static com.workoutly.measurement.utils.ResponseValidator.*;
import static com.workoutly.measurement.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class BodyMeasurementControllerTest {
    private final static String CREATE_MEASUREMENT_URL = "/api/measurements/body/create";
    private final static String UPDATE_MEASUREMENT_URL = "/api/measurements/body/update";
    private final static String DELETE_MEASUREMENT_URL = "/api/measurements/body/delete";
    private final static String SUMMARY_MEASUREMENT_URL = "/api/measurements/body/summary";
    private final static String PAGE_MEASUREMENT_URL = "/api/measurements/body";

    private MockMvc mockMvc;

    @Mock
    private MeasurementsApplicationService service;

    @InjectMocks
    private BodyMeasurementController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new MockExceptionHandler())
                .build();
    }


    @Test
    public void testCreateBodyMeasurement() {
        //given
        var command = createSampleBodyMeasurementCommand();
        var response = new MessageResponse("Body measurement has been created");

        doReturn(response)
                .when(service)
                .createBodyMeasurement(command);

        //when
        performCreateBodyMeasurementCommand(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testCreateBodyMeasurementFailure() {
        //given
        var command = createSampleBodyMeasurementCommand();

        doThrow(new ValidationException())
                .when(service)
                .createBodyMeasurement(command);

        //when
        performCreateBodyMeasurementCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    @Test
    public void testUpdateBodyMeasurement() {
        //given
        var command = createSampleBodyMeasurementCommand();
        var response = new MessageResponse("Body measurement has been updated");

        doReturn(response)
                .when(service)
                .updateBodyMeasurement(command);

        //when
        performUpdateBodyMeasurementCommand(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testUpdateBodyMeasurementFailure() {
        //given
        var command = createSampleBodyMeasurementCommand();

        doThrow(new ValidationException())
                .when(service)
                .updateBodyMeasurement(command);

        //when
        performUpdateBodyMeasurementCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    @Test
    public void testDeleteBodyMeasurement() {
        //given
        var command = new DeleteMeasurementCommand(Date.from(Instant.now()));
        var response = new MessageResponse("Body measurement has been deleted");

        doReturn(response)
                .when(service)
                .deleteBodyMeasurement(command);

        //when
        performDeleteBodyMeasurementCommand(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testDeleteBodyMeasurementFailure() {
        //given
        var command = new DeleteMeasurementCommand(Date.from(Instant.now()));

        doThrow(new ValidationException())
                .when(service)
                .deleteBodyMeasurement(command);

        //when
        performDeleteBodyMeasurementCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    @Test
    public void testFindSummaryBodyMeasurements() {
        //given
        var snapshots = List.of(createSampleBodyMeasurementSnapshot());
        var response = new BodyMeasurementsResponse(snapshots);

        doReturn(response)
                .when(service)
                .findSummaryBodyMeasurements();

        //when
        performFindSummaryBodyMeasurements();

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testFindBodyMeasurements() {
        //given
        var command = new MeasurementsPageQuery(1);
        var snapshots = List.of(createSampleBodyMeasurementSnapshot());
        var response = new BodyMeasurementsResponse(snapshots);

        doReturn(response)
                .when(service)
                .findBodyMeasurements(command);

        //when
        performFindBodyMeasurements(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testFindBodyMeasurementsFailure() {
        //given
        var command = new MeasurementsPageQuery(1);

        doThrow(new ValidationException())
                .when(service)
                .findBodyMeasurements(command);

        //when
        performFindBodyMeasurements(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    private BodyMeasurementCommand createSampleBodyMeasurementCommand() {
        return new BodyMeasurementCommand(
                10,11,12,13,14,15,16,17,18,19,20,
                Date.from(Instant.now())
        );
    }

    private BodyMeasurementSnapshot createSampleBodyMeasurementSnapshot() {
        return new BodyMeasurementSnapshot(
                new BodyMeasurementId(UUID.randomUUID()),
                10,11,12,13,14,15,16,17,18,19,20,
                Date.from(Instant.now()), "test"
        );
    }

    @SneakyThrows
    private void performCreateBodyMeasurementCommand(BodyMeasurementCommand request) {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(CREATE_MEASUREMENT_URL)
                        .content(mapToString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }

    @SneakyThrows
    private void performUpdateBodyMeasurementCommand(BodyMeasurementCommand request) {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put(UPDATE_MEASUREMENT_URL)
                        .content(mapToString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }


    @SneakyThrows
    private void performDeleteBodyMeasurementCommand(DeleteMeasurementCommand request) {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete(DELETE_MEASUREMENT_URL)
                        .content(mapToString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }

    @SneakyThrows
    private void performFindSummaryBodyMeasurements() {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(SUMMARY_MEASUREMENT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }

    @SneakyThrows
    private void performFindBodyMeasurements(MeasurementsPageQuery request) {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(PAGE_MEASUREMENT_URL)
                        .content(mapToString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }


    private void assertResponseStatusIs(int status) {
        assertEquals(status, responseStatusIs());
    }

    private void assertResponseContentIs(Object response) {
        assertEquals(mapToString(response), responseContentIs());
    }

    private ErrorResponse errorResponse() {
        return createErrorResponse();
    }
}
