package com.example.entity;

import com.example.dto.PredictionDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prediction")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String predictedWinner;

    public PredictionDto toDto() {
        return PredictionDto.builder()
                .predictionId(id)
                .predictedWinner(predictedWinner)
                .build();
    }
}
