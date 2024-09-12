package com.example.demo.services;

import com.example.demo.entities.MatchGame;

import java.util.List;
import java.util.Optional;

public interface MathCrudService {
    MatchGame createMatch(MatchGame match);
    Optional<MatchGame> getMatchById(Long id);
    List<MatchGame> getAllMatches();
    MatchGame updateMatch(Long id,MatchGame match);
    void deleteMatchById(Long id);
}
