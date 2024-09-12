package com.example.demo.services;

import com.example.demo.constants.CrudConstants;
import com.example.demo.entities.Player;
import com.example.demo.entities.Team;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.exceptions.TeamNotFoundException;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TeamCrudServiceImpl implements TeamCrudService {
    private final static String TEAM_NOT_FOUND_WITH_ID
            = CrudConstants.TEAM_NOT_FOUND_WITH_ID;
    @Autowired
    private TeamRepository teamRepository;
    @Override
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team updateTeam(Long id, Team teamUpdate) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException
                        (TEAM_NOT_FOUND_WITH_ID + id));
        team.setId(teamUpdate.getId());
        team.setTeamName(teamUpdate.getTeamName());
        team.setManagerName(teamUpdate.getManagerName());
        team.setGroupName(teamUpdate.getGroupName());
        return teamRepository.save(team);
    }

    @Override
    public void deleteTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException
                        (TEAM_NOT_FOUND_WITH_ID + id));
        teamRepository.delete(team);
    }
}
