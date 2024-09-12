package com.example.demo.services;

import com.example.demo.constants.CrudConstants;
import com.example.demo.entities.MatchGame;
import com.example.demo.entities.Player;
import com.example.demo.exceptions.MatchNotFoundException;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MathCrudServiceImpl implements MathCrudService {
    public static final String MATCH_NOT_FOUND_WITH_ID
            = CrudConstants.MATCH_NOT_FOUND_WITH_ID;
    @Autowired
    private MatchRepository matchRepository;

    @Override
    public MatchGame createMatch(MatchGame match) {
        return matchRepository.save(match);
    }

    @Override
    public Optional<MatchGame> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    @Override
    public List<MatchGame> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public MatchGame updateMatch(Long id, MatchGame matchUpdate) {
        MatchGame matchGame = matchRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException
                        (MATCH_NOT_FOUND_WITH_ID + id));
        matchGame.setId(matchUpdate.getId());
        matchGame.setFirstTeamId(matchUpdate.getFirstTeamId());
        matchGame.setSecondTeamId(matchUpdate.getSecondTeamId());
        matchGame.setDatePlayed(matchUpdate.getDatePlayed());
        matchGame.setScore(matchUpdate.getScore());
        return matchRepository.save(matchGame);
    }


    @Override
    public void deleteMatchById(Long id) {
        MatchGame match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException
                        (MATCH_NOT_FOUND_WITH_ID + id));
        matchRepository.delete(match);
    }
}
