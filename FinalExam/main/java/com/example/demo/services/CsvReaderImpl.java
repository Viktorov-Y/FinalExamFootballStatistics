package com.example.demo.services;

import com.example.demo.constants.DateConstants;
import com.example.demo.constants.FilePathConstants;
import com.example.demo.entities.MatchGame;
import com.example.demo.entities.Player;
import com.example.demo.entities.Record;
import com.example.demo.entities.Team;
import com.example.demo.repositories.MatchRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CsvReaderImpl implements CsvReader {
    //Paths to the CSV files
    private static final String TEAMS_FILE_PATH = FilePathConstants.TEAMS_FILE_PATH;
    private static final String PLAYERS_FILE_PATH = FilePathConstants.PLAYERS_FILE_PATH;
    private static final String MATCHES_FILE_PATH = FilePathConstants.MATCHES_FILE_PATH;
    private static final String RECORDS_FILE_PATH = FilePathConstants.RECORDS_FILE_PATH;
    //Date formats to be supported
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateConstants.DATE_TIME_FORMATTER;
    //Format for converting LocalDate to Date
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT
            = DateConstants.SIMPLE_DATE_FORMAT;
    //Logger for logging errors and information
    private static final Logger LOGGER = Logger.getLogger(CsvReaderImpl.class.getName());
    public static final int FULL_MATCH_TIME = 90;
    public static final String CSV_SPLITTER = ",";

    static {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.ALL);
    }

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private Validator validator;

    public CsvReaderImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    //Method to read matches from CSV file
    public List<MatchGame> readMatches() {
        List<MatchGame> matches = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(MATCHES_FILE_PATH))) {
            String line;
            boolean ifFirstLine = true;
            while ((line = bf.readLine()) != null) {
                if (ifFirstLine) {
                    ifFirstLine = false;
                    continue;
                }
                MatchGame match = new MatchGame();
                String[] tokens = line.split(CSV_SPLITTER);
                try {
                    match.setId(Long.parseLong(tokens[0]));
                    match.setFirstTeamId(Long.parseLong(tokens[1]));
                    match.setSecondTeamId(Long.parseLong(tokens[2]));
                    LocalDate parsedDate = parseDate(tokens[3]);
                    if (parsedDate != null) {
                        match.setDatePlayed(convertToDate(parsedDate));
                    } else {
                        LOGGER.log(Level.WARNING, "Invalid date: {0}", tokens[3]);
                        continue;
                    }
                    match.setScore(tokens[4]);
                    //Validate the match object
                    Set<ConstraintViolation<MatchGame>> violations = validator.validate(match);
                    if (!violations.isEmpty()) {
                        for (ConstraintViolation<MatchGame> violation : violations) {
                            LOGGER.log(Level.ALL, "Validation error in match {0}-{1}"
                                    , new Object[]{match.getId(), violation.getMessage()});
                        }
                        //Skip the match and continue
                        continue;
                    }
                    matches.add(match);
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.SEVERE, "Failed to parse number: {0}", e.getMessage());
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Unexpected errro :{0}", e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read file {0}", e.getMessage());
        }
        return matches;
    }

    //Method to read teams from CSV file
    public List<Team> readTeams() {
        List<Team> teams = new ArrayList<>();
        System.out.println(TEAMS_FILE_PATH);
        try (BufferedReader bf = new BufferedReader(new FileReader(TEAMS_FILE_PATH))) {
            String line;
            boolean isFirstLine = true;
            while ((line = bf.readLine()) != null) {
                if (isFirstLine == true) {
                    isFirstLine = false;
                    continue;
                }
                String[] tokens = line.split(CSV_SPLITTER);
                Team team = new Team();
                try {
                    team.setId(Long.parseLong(tokens[0]));
                    team.setTeamName(tokens[1]);
                    team.setManagerName(tokens[2]);
                    team.setGroupName(tokens[3]);
                    //Validate the team object
                    Set<ConstraintViolation<Team>> violations = validator.validate(team);
                    if (!violations.isEmpty()) {
                        for (ConstraintViolation<Team> violation : violations) {
                            LOGGER.log(Level.ALL, "Validation error in team {0}-{1}"
                                    , new Object[]{team.getTeamName(), violation.getMessage()});
                        }
                        //Skip the team and continue
                        continue;
                    }
                    teams.add(team);
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.SEVERE, "Failed to parse number {0}", e.getMessage());
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Unexpected error {0}", e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read file {0}", e.getMessage());
        }
        return teams;
    }

    //Method to read players from CSV file
    public List<Player> readPlayers() {
        List<Player> players = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(PLAYERS_FILE_PATH))) {
            String line;
            boolean ifFirstLine = true;
            while ((line = bf.readLine()) != null) {
                if (ifFirstLine) {
                    ifFirstLine = false;
                    continue;
                }
                String[] tokens = line.split(CSV_SPLITTER);
                Player player = new Player();
                try {
                    player.setId(Long.parseLong(tokens[0]));
                    player.setTeamNumber(Integer.parseInt(tokens[1]));
                    player.setPosition(tokens[2]);
                    player.setFullName(tokens[3]);
                    player.setTeamId(Long.parseLong(tokens[4]));
                    //Validate the player object
                    Set<ConstraintViolation<Player>> violations = validator.validate(player);
                    if (!violations.isEmpty()) {
                        for (ConstraintViolation<Player> violation : violations) {
                            LOGGER.log(Level.ALL, "Validation error in player {0}-{1}"
                                    , new Object[]{player.getId(), violation.getMessage()});
                        }
                        //Skip the player and continue
                        continue;
                    }
                    players.add(player);
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.SEVERE, "Failed to parse number {0}", e.getMessage());
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Unexpected error {0}", e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read file {0}", e.getMessage());
        }
        return players;
    }

    //Method to read records from CSV file
    public List<Record> readRecords() {
        List<Record> records = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(RECORDS_FILE_PATH))) {
            String line;
            boolean ifFirstLine = true;
            while ((line = bf.readLine()) != null) {
                if (ifFirstLine) {
                    ifFirstLine = false;
                    continue;
                }
                Record record = new Record();
                String[] tokens = line.split(CSV_SPLITTER);
                try {
                    record.setId(Long.parseLong(tokens[0]));
                    record.setPlayerId(Long.parseLong(tokens[1]));
                    Long matchId = Long.parseLong(tokens[2]);
                    record.setMatchId(matchId);
                    record.setFromMin(Integer.parseInt(tokens[3]));
//                boolean flag = hasPenalties(matchId);
                    if (tokens[4].equalsIgnoreCase("NULL")) {
                        record.setToMin(FULL_MATCH_TIME);
                    }
//                else if (tokens[4].equalsIgnoreCase("NULL")
//                        && hasPenalties(matchId)) {
//                    record.setToMin(120);
//                }
                    else {
                        record.setToMin(Integer.parseInt(tokens[4]));
                    }
                    //Validate the record object
                    Set<ConstraintViolation<Record>> violations = validator.validate(record);
                    if (!violations.isEmpty()) {
                        for (ConstraintViolation<Record> violation : violations) {
                            LOGGER.log(Level.ALL, "Validation error in record {0}-{1}"
                                    , new Object[]{record.getId(), violation.getMessage()});
                        }
                        //Skip the record and continue
                        continue;
                    }
                    records.add(record);
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.SEVERE, "Failed to parse number {0}", e.getMessage());
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Unexpected error {0}", e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read file {0}", e.getMessage());
        }
        return records;
    }
    //Method to parse date from text format
    private LocalDate parseDate(String dateText) {
        try {
            return LocalDate.parse(dateText, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            System.err.println("Failed to parse date " + dateText);
            return null;
        }
    }

    //Method to convert LocalDate to Date
    private Date convertToDate(LocalDate localDate) {
        try {
            return SIMPLE_DATE_FORMAT.parse(localDate.toString());
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Failed to convert LocalDate to Date: {0}", e.getMessage());
            return null;
        }
    }

}
