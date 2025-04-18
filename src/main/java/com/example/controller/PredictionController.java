package com.example.controller;

import com.example.dto.PredictionDto;
import com.example.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<PredictionDto> create(@RequestBody PredictionDto predictionDto) {

        var savedPrediction = predictionService.save(predictionDto);
        var createdPrediction = predictionDto
            .withPredictionId(savedPrediction.getId())
            .withPredictedWinner(savedPrediction.getPredictedWinner())
            ;

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{productId}")
                .buildAndExpand(createdPrediction.predictionId())
                .toUri();

        return ResponseEntity.created(uri).body(createdPrediction);
    }

}
