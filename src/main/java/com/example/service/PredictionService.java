package com.example.service;

import com.example.dto.PredictionDto;
import com.example.entity.Prediction;
import com.example.entity.User;
import com.example.repository.PredictionRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PredictionService {
    final PredictionRepository predictionRepository;
    final UserRepository userRepository;

    @Autowired
    public PredictionService(PredictionRepository predictionRepository, UserRepository userRepository) {
        this.predictionRepository = predictionRepository;
        this.userRepository = userRepository;
    }

    public Prediction save(PredictionDto predictionDto) {
        // find the user
        Optional<User> users = userRepository.findById(predictionDto.userId());
        User user = users.orElseThrow(); // todo more appropriate error type

        Prediction prediction = new Prediction();
        prediction.setPredictedWinner(predictionDto.predictedWinner());
        prediction.setUser(user);

        return predictionRepository.save(prediction);
    }

    public Optional<Prediction> findById(long predictionId) {
        return predictionRepository.findById(predictionId);
    }
}
