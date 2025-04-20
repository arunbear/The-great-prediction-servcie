package com.example.controller;

import com.example.dto.PredictionDto;
import com.example.entity.Match;
import com.example.entity.Prediction;
import com.example.entity.User;
import com.example.service.PredictionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PredictionController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PredictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private PredictionService predictionService;

    @Test
    void uses_predictionService_to_create_a_prediction() throws Exception {
        // given ...
        var prediction = Prediction.builder()
                .id(1L)
                .predictedWinner("ABC")
                .user(new User(1L))
                .match(new Match(1L))
                .build();

        when(predictionService.save(any(PredictionDto.class)))
            .thenReturn(prediction);

        // when ...
        mockMvc.perform(
            post("/prediction")
                .content(objectMapper.writeValueAsString(prediction))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.predictionId").value(prediction.getId()))
            .andExpect(jsonPath("$.predictedWinner").value(prediction.getPredictedWinner()))
        ;

    }

    @Test
    void uses_predictionService_to_find_a_prediction() throws Exception {
        // given ...
        long predictionId = 1;

        // when ...
        mockMvc.perform( get("/prediction/%s".formatted(predictionId)) );

        verify(predictionService).findById(predictionId);

    }
}