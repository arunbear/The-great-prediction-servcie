package com.example.dto;

import lombok.Builder;
import lombok.With;

@Builder
@With
public record PredictionResponse(
        PredictionDto predictionDto,
        Status status
) {
}
