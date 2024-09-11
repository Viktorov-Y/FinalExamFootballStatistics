package com.example.demo.services;

import com.example.demo.entities.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerCrudService {
    Player createPlayer(Player player);
    Optional<Player> getPlayerById(Long id);
    List<Player> getAllPlayers();

    Player updatePlayer(Long id, Player player);

    void deletePlayerById(Long id);
}
