package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Team name cannot be empty")
    @Size(max=100,message="Team name must be less than 100 symbols")
    private String teamName;
    @NotBlank(message="Manager name cannot be empty")
    @Size(max=100,message = "Manager name must be less than 100 symbols")
    private String managerName;
    @NotBlank(message = "Group name cannot be empty")
    @Size(max = 1,message = "Group name must be a single character")
    private String groupName;

}
