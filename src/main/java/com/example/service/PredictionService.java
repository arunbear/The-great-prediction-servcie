package com.example.service;

import com.example.dto.PredictionDto;
import com.example.entity.Match;
import com.example.entity.Prediction;
import com.example.entity.User;
import com.example.repository.MatchRepository;
import com.example.repository.PredictionRepository;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredictionService {
    final PredictionRepository predictionRepository;
    final UserRepository userRepository;
    final MatchRepository matchRepository;

    private static final Logger logger = LoggerFactory.getLogger(PredictionService.class);

    @Autowired
    public PredictionService(PredictionRepository predictionRepository, UserRepository userRepository, MatchRepository matchRepository) {
        this.predictionRepository = predictionRepository;
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }

    public Prediction save(PredictionDto predictionDto) {
        // find related user
        Optional<User> users = userRepository.findById(predictionDto.userId());
        User user = users.orElseThrow(); // todo more appropriate error type

        // find related match
        Optional<Match> matches = matchRepository.findById(predictionDto.userId());
        Match match = matches.orElseThrow(); // todo more appropriate error type

        Prediction prediction = new Prediction();
        prediction.setPredictedWinner(predictionDto.predictedWinner());
        prediction.setUser(user);
        prediction.setMatch(match);

        return predictionRepository.save(prediction);
    }

    public Optional<Prediction> findById(long predictionId) {
        return predictionRepository.findById(predictionId);
    }

    public List<PredictionDto> findByUser(long userId) {
        List<Prediction> predictions = predictionRepository.findByUser_Id(userId);
        return predictions
                .stream()
                .map(Prediction::toDto)
                .toList();
    }

    public Optional<Prediction> update(long predictionId, PredictionDto predictionDto) {
        Optional<Prediction> predictions = predictionRepository.findById(predictionId);
        if (predictions.isEmpty()) {
            logger.warn("Prediction with id {} not found", predictionId);
            return predictions;
        }

        Prediction prediction = predictions.get();
        prediction.setPredictedWinner(predictionDto.predictedWinner());

        // todo update match as well

        return Optional.of(predictionRepository.save(prediction));
    }
}
