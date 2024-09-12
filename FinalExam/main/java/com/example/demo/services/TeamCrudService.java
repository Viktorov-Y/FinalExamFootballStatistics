package com.example.demo.services;

import com.example.demo.entities.Player;
import com.example.demo.entities.Team;

import java.util.List;
import java.util.Optional;

public interface TeamCrudService {
    Team createTeam(Team team);
    Optional<Team> getTeamById(Long id);
    List<Team> getAllTeams();
    Team updateTeam(Long id,Team team);
    void deleteTeamById(Long id);
}
