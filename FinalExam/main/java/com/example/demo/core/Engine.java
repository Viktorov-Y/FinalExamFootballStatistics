package com.example.demo.core;

import com.example.demo.entities.MatchGame;
import com.example.demo.entities.Player;
import com.example.demo.entities.Record;
import com.example.demo.entities.Team;
import com.example.demo.repositories.MatchRepository;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.repositories.RecordRepository;
import com.example.demo.repositories.TeamRepository;
import com.example.demo.services.CsvReader;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Engine implements Runnable {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private PlayerService playerService;

    public Engine() {
    }

    @Override
    public void run() {
        List<MatchGame> matches = csvReader.readMatches();
        matchRepository.saveAll(matches);
        List<Team> teams = csvReader.readTeams();
        List<Player> players = csvReader.readPlayers();
        List<Record> records = csvReader.readRecords();
        teamRepository.saveAll(teams);
        playerRepository.saveAll(players);
        recordRepository.saveAll(records);
       List<String> playingPairs = playerService.findAllPlayingPairs();
        System.out.println(playingPairs.get(0));
    }
}
