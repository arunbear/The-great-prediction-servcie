package com.example.repository;

import com.example.entity.Match;
import com.example.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
