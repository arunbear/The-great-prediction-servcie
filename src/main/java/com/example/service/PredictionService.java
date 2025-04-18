package com.example.service;

import com.example.dto.PredictionDto;
import com.example.entity.Prediction;
import com.example.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {
    final PredictionRepository predictionRepository;

    @Autowired
    public PredictionService(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    public Prediction save(PredictionDto predictionDto) {
        Prediction prediction = new Prediction();
        prediction.setPredictedWinner(predictionDto.predictedWinner());

        return predictionRepository.save(prediction);
    }
}
