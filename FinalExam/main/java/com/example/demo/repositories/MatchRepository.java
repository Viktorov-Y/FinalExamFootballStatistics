package com.example.demo.repositories;

 import com.example.demo.entities.MatchGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchGame,Long> {
}
