package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Team number cannot be emtpy")
    private int teamNumber;
    @NotBlank(message = "Position cannot be empty")
    @Size(max=2,message = "Position must be less than 2 characters")
    private String position;
    @NotBlank(message = "Full name cannot be empty")
    @Size(max = 100,message = "Full name must be less than 100 characters")
    private String fullName;
    @NotNull(message = "Team Id cannot be empty")
    private Long teamId;

}
