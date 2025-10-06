package tech.steve.essaie.dto.ferme;


import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.time.LocalDateTime;



public record FermeDto(@NotBlank
                       String nom,

                       String adresse,

                       String telephone,

                       String email) {

}

