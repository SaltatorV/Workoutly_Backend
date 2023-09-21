package com.workoutly.measurement.api;

import com.workoutly.common.exception.ErrorResponse;
import com.workoutly.measurement.VO.BodyWeightId;
import com.workoutly.measurement.VO.BodyWeightSnapshot;
import com.workoutly.measurement.dto.command.BodyMeasurementCommand;
import com.workoutly.measurement.dto.command.BodyWeightCommand;
import com.workoutly.measurement.dto.command.DeleteMeasurementCommand;
import com.workoutly.measurement.dto.query.MeasurementsPageQuery;
import com.workoutly.measurement.dto.response.BodyMeasurementsResponse;
import com.workoutly.measurement.dto.response.BodyWeightsResponse;
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
import static com.workoutly.measurement.utils.ResponseValidator.responseContentIs;
import static com.workoutly.measurement.utils.TestUtils.mapToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class BodyWeightControllerTest {
    private final static String CREATE_MEASUREMENT_URL = "/api/measurements/weight/create";
    private final static String UPDATE_MEASUREMENT_URL = "/api/measurements/weight/update";
    private final static String DELETE_MEASUREMENT_URL = "/api/measurements/weight/delete";
    private final static String SUMMARY_MEASUREMENT_URL = "/api/measurements/weight/summary";
    private final static String PAGE_MEASUREMENT_URL = "/api/measurements/weight";

    private MockMvc mockMvc;

    @Mock
    private MeasurementsApplicationService service;

    @InjectMocks
    private BodyWeightController controller;

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
        var command = createSampleBodyWeightCommand();
        var response = new MessageResponse("Body weight has been created");

        doReturn(response)
                .when(service)
                .createBodyWeight(command);

        //when
        performCreateBodyWeightCommand(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testCreateBodyMeasurementFailure() {
        //given
        var command = createSampleBodyWeightCommand();

        doThrow(new ValidationException())
                .when(service)
                .createBodyWeight(command);

        //when
        performCreateBodyWeightCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    @Test
    public void testUpdateBodyMeasurement() {
        //given
        var command = createSampleBodyWeightCommand();
        var response = new MessageResponse("Body weight has been updated");

        doReturn(response)
                .when(service)
                .updateBodyWeight(command);

        //when
        performUpdateBodyWeightCommand(command);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testUpdateBodyMeasurementFailure() {
        //given
        var command = createSampleBodyWeightCommand();

        doThrow(new ValidationException())
                .when(service)
                .updateBodyWeight(command);

        //when
        performUpdateBodyWeightCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    @Test
    public void testDeleteBodyMeasurement() {
        //given
        var command = new DeleteMeasurementCommand(Date.from(Instant.now()));
        var response = new MessageResponse("Body weight has been deleted");

        doReturn(response)
                .when(service)
                .deleteBodyWeight(command);

        //when
        performDeleteBodyWeightCommand(command);

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
                .deleteBodyWeight(command);

        //when
        performDeleteBodyWeightCommand(command);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    @Test
    public void testFindSummaryBodyMeasurements() {
        //given
        var snapshots = List.of(createSampleBodyWeightSnapshot());
        var response = new BodyWeightsResponse(snapshots);

        doReturn(response)
                .when(service)
                .findSummaryBodyWeights();

        //when
        performFindSummaryBodyWeights();

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testFindBodyMeasurements() {
        //given
        var query = new MeasurementsPageQuery(1);
        var snapshots = List.of(createSampleBodyWeightSnapshot());
        var response = new BodyWeightsResponse(snapshots);

        doReturn(response)
                .when(service)
                .findBodyWeights(query);

        //when
        performFindBodyWeights(query);

        //then
        assertResponseStatusIs(isOk());
        assertResponseContentIs(response);
    }

    @Test
    public void testFindBodyMeasurementsFailure() {
        //given
        var query = new MeasurementsPageQuery(1);

        doThrow(new ValidationException())
                .when(service)
                .findBodyWeights(query);

        //when
        performFindBodyWeights(query);

        //then
        assertResponseStatusIs(isBadRequest());
        assertResponseContentIs(errorResponse());
    }

    private BodyWeightCommand createSampleBodyWeightCommand() {
        return new BodyWeightCommand(
                100,21,
                Date.from(Instant.now())
        );
    }

    private BodyWeightSnapshot createSampleBodyWeightSnapshot() {
        return new BodyWeightSnapshot(
                new BodyWeightId(UUID.randomUUID()),
                100,21,
                Date.from(Instant.now()), "test"
        );
    }

    @SneakyThrows
    private void performCreateBodyWeightCommand(BodyWeightCommand request) {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(CREATE_MEASUREMENT_URL)
                        .content(mapToString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }

    @SneakyThrows
    private void performUpdateBodyWeightCommand(BodyWeightCommand request) {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put(UPDATE_MEASUREMENT_URL)
                        .content(mapToString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }


    @SneakyThrows
    private void performDeleteBodyWeightCommand(DeleteMeasurementCommand request) {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete(DELETE_MEASUREMENT_URL)
                        .content(mapToString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }

    @SneakyThrows
    private void performFindSummaryBodyWeights() {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(SUMMARY_MEASUREMENT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn();

        setResult(result);
    }

    @SneakyThrows
    private void performFindBodyWeights(MeasurementsPageQuery request) {
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
