package com.example.service;

import com.example.dto.PredictionDto;
import com.example.entity.Prediction;
import com.example.entity.User;
import com.example.repository.PredictionRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PredictionServiceTest {
    @Mock
    PredictionRepository predictionRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    PredictionService predictionService;

    @Test
    void uses_repositories_to_save_a_prediction() {
        // given ...
        long userId = 1L;
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(new User(userId)));

        PredictionDto predictionDto = PredictionDto.builder()
                .predictedWinner("Brentford")
                .userId(userId)
                .build();
        // when ...
        predictionService.save(predictionDto);

        // then ...
        verify(userRepository).findById(userId);
        verify(predictionRepository).save(any(Prediction.class));
    }

    @Test
    void uses_a_repository_to_find_a_prediction() {
        // given ...
        long predictionId = 1L;

        // when ...
        predictionService.findById(predictionId);

        verify(predictionRepository).findById(predictionId);
    }
}