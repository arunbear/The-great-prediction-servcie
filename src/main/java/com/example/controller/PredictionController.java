package com.example.controller;

import com.example.dto.PredictionDto;
import com.example.dto.PredictionResponse;
import com.example.entity.Prediction;
import com.example.service.PredictionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PredictionController {
    private final PredictionService predictionService;

    private static final Logger logger = LoggerFactory.getLogger(PredictionController.class);

    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/prediction")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PredictionDto> create(@Valid @RequestBody PredictionDto predictionDto) {

        var savedPrediction = predictionService.save(predictionDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{productId}")
                .buildAndExpand(savedPrediction.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(savedPrediction.toDto());
    }

    @GetMapping("/prediction/{id}")
    public ResponseEntity<PredictionDto> getPredictionById(@PathVariable long id) {
        Optional<Prediction> predictions = predictionService.findById(id);

        return predictions
                .map(prediction -> ResponseEntity.ok(prediction.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PatchMapping("/prediction/{id}")
    public ResponseEntity<PredictionResponse> updatePrediction(@PathVariable long id, @Valid @RequestBody PredictionDto predictionDto) {
        PredictionResponse response = predictionService.update(id, predictionDto);
        logger.info(response.toString());

        return switch (response.status()) {
            case SUCCESS     -> ResponseEntity.ok(response);
            case NOT_FOUND   -> ResponseEntity.notFound().build();
            case NOT_UPDATED -> ResponseEntity.unprocessableEntity().body(response);
        };
    }

    @GetMapping("/user/{userId}/predictions")
    public ResponseEntity<List<PredictionDto>> getPredictionsForUser(@PathVariable long userId) {
        List<PredictionDto> predictions = predictionService.findByUser(userId);

        return ResponseEntity.ok(predictions);
    }

}
