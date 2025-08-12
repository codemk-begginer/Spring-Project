package tech.steve.farmer_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlerteDto {

    private Long id;

    private Long animalId;

    private Long fermeId;

    private String type;

    private String message;

    private LocalDate date;

    private boolean vue;

    private String statut; // Exemple : ACTIF, TRAITÉ, ARCHIVÉ
}