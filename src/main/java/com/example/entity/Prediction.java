package com.example.entity;

import com.example.dto.PredictionDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    public PredictionDto toDto() {
        return PredictionDto.builder()
                .predictionId(id)
                .predictedWinner(predictedWinner)
                .userId(user.getId())
                .matchId(match.getId())
                .build();
    }

    public boolean isOpen() {
        return getMatch().getStartTime().isAfter(LocalDateTime.now()); // todo move logic to Match
    }

    public boolean isClosed() {
        return !isOpen();
    }
}
