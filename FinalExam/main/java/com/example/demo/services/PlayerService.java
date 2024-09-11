package com.example.demo.services;

import com.example.demo.entities.Record;
import com.example.demo.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerService {
    @Autowired
    private RecordRepository recordRepository;

    /**
     * Finds all playing pairs and returns a list of results.
     * @return List of results for each pair of players.
     */
    public List<String> findAllPlayingPairs() {
        List<Record> records = recordRepository.findAll();
        Map<String, Long> pairTimeMap = new HashMap<>();
        Map<String, Map<Long, Long>> matchTimeMap = new HashMap<>();
        // Process records to find pairs of players and the time they played together
        for (Record r1 : records) {
            for (Record r2 : records) {
                if (!r1.getPlayerId().equals(r2.getPlayerId()) &&
                        r1.getMatchId().equals(r2.getMatchId())) {
                    int start = Math.max(r1.getFromMin(), r2.getFromMin());
                    int end = Math.min(r1.getToMin(), r2.getToMin());
                    if (start < end) {
                        String pairKey = r1.getPlayerId() < r2.getPlayerId() ?
                                r1.getPlayerId() + "-" + r2.getPlayerId() :
                                r2.getPlayerId() + "-" + r1.getPlayerId();
                        long duration = end - start;
                        pairTimeMap.putIfAbsent(pairKey, pairTimeMap.getOrDefault
                                (pairKey, 0L) + duration);
                        matchTimeMap.putIfAbsent(pairKey, new HashMap<>());
                        matchTimeMap.get(pairKey).put(r1.getMatchId(), duration);
                    }
                }
            }
        }
        // Sort pairs by total playing time
        ArrayList<Map.Entry<String, Map<Long, Long>>> sortedByTime
                = new ArrayList<>(matchTimeMap.entrySet());
        sortedByTime.sort((e1, e2) -> {
            long totalDuration1 = e1.getValue().values().stream().mapToLong(Long::longValue).sum();
            long totalDuratioin2 = e2.getValue().values().stream().mapToLong(Long::longValue).sum();
            return Long.compare(totalDuratioin2, totalDuration1);
        });
        List<String> results = new ArrayList<>();
        // Create results for each pair of players
        for (Map.Entry<String, Map<Long, Long>> entry : sortedByTime) {
            String pair = entry.getKey();
            long totalDuration = 0l;
            StringBuilder result = new StringBuilder();
            for (Map.Entry<Long, Long> matchEntry : matchTimeMap.get(pair).entrySet()) {
                totalDuration += matchEntry.getValue();
            }
            result.append(pair).append(" = ").append(totalDuration);
//            result.append("Players ").append(pair).append(" played together for ")
//                    .append(totalDuration).append(" minutes");
            results.add(result.toString());
        }
        return results;
    }
}
