package com.example.service;

import com.example.dto.PredictionDto;
import com.example.entity.Prediction;
import com.example.repository.PredictionRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PredictionServiceTest {
    @Mock
    PredictionRepository predictionRepository;

    @InjectMocks
    PredictionService predictionService;

    @Test
    void uses_a_repository_to_save_prediction() {
        PredictionDto predictionDto = PredictionDto.builder().predictedWinner("Brentford").build();
        predictionService.save(predictionDto);

        verify(predictionRepository).save(any(Prediction.class));
    }
}