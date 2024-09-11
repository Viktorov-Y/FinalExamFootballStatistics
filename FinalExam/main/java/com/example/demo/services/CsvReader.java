package com.example.demo.services;

 import com.example.demo.entities.MatchGame;
import com.example.demo.entities.Player;
import com.example.demo.entities.Record;
import com.example.demo.entities.Team;

import java.io.FileNotFoundException;
import java.util.List;

public interface CsvReader {
    List<Team> readTeams();
    List<Player> readPlayers();
    List<MatchGame> readMatches() ;
    List<Record> readRecords();

}
