package com.example.demo.services;

import com.example.demo.constants.CrudConstants;
import com.example.demo.entities.Player;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerCrudServiceImpl implements PlayerCrudService {
    public static final String PLAYER_NOT_FOUND_WITH_ID
            = CrudConstants.PLAYER_NOT_FOUND_WITH_ID;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player updatePlayer(Long id, Player playerUpdate) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException
                        (PLAYER_NOT_FOUND_WITH_ID + id));
        player.setTeamNumber(playerUpdate.getTeamNumber());
        player.setPosition(playerUpdate.getPosition());
        player.setFullName(playerUpdate.getFullName());
        player.setTeamId(playerUpdate.getTeamId());
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException
                        (PLAYER_NOT_FOUND_WITH_ID + id));
        playerRepository.delete(player);
    }
}
