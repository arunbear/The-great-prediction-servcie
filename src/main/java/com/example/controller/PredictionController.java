package com.example.controller;

import com.example.dto.PredictionDto;
import com.example.entity.Prediction;
import com.example.service.PredictionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/prediction")
public class PredictionController {
    private final PredictionService predictionService;

    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping
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

    @GetMapping("/{id}")
    public ResponseEntity<PredictionDto> getPredictionById(@PathVariable long id) {
        Optional<Prediction> predictions = predictionService.findById(id);

        return predictions
                .map(prediction -> ResponseEntity.ok(prediction.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

}
