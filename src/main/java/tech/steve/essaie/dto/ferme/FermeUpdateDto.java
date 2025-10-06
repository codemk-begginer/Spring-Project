package tech.steve.essaie.dto.ferme;

import java.time.Instant;
import java.time.LocalDateTime;

public record FermeUpdateDto(
         String nom,
         String adresse,
         String telephone,
         String email
         ) { }
