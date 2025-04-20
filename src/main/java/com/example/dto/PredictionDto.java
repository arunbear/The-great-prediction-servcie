package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.With;

@Builder
@With
public record PredictionDto(
        long predictionId,
        long userId,
        long matchId,

        @NotBlank(message = "must be present")
        String predictedWinner
) {
}
