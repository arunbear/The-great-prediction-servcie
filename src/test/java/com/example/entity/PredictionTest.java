package com.example.entity;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PredictionTest {
    @Test
    void a_prediction_is_open_if_its_match_has_not_started() {
        Match match = new Match(1L, LocalDateTime.MAX);

        Prediction prediction = new Prediction();
        prediction.setMatch(match);

        then(prediction.isOpen()).isTrue();
    }

    @Test
    void a_prediction_is_closed_if_its_match_has_started() {
        Match match = new Match(1L, LocalDateTime.MIN);

        Prediction prediction = new Prediction();
        prediction.setMatch(match);

        then(prediction.isClosed()).isTrue();
    }

}