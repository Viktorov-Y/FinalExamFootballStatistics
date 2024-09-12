package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Record {
    public static final String PLAYER_ID_NOT_EMPTY = "Player id cannot be empty";
    public static final String MATCH_ID_NOT_EMPTY = "Match id cannot be empty";
    public static final String FROM_MIN_AT_LEAST_ZERO = "From minutes must be at least 0";
    public static final String TO_MIN_AT_LEAST_ZERO = "To minutes must be at least 0";
    public static final String TO_MIN_AT_MOST_NINETY = "To minutes must be at most 90";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = PLAYER_ID_NOT_EMPTY)
    private Long playerId;
    @NotNull(message = MATCH_ID_NOT_EMPTY)
    private Long matchId;
    @Min(value = 0, message = FROM_MIN_AT_LEAST_ZERO)
    private int fromMin;
    @Min(value = 0, message = TO_MIN_AT_LEAST_ZERO)
    @Max(value = 90, message = TO_MIN_AT_MOST_NINETY)
    private int toMin;

}
