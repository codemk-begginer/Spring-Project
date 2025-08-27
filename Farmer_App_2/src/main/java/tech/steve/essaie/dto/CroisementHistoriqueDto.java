package tech.steve.essaie.dto;

import tech.steve.essaie.enums.StatutGestation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CroisementHistoriqueDto {
    private String role; // "MERE" ou "PERE"
    private Long partenaireId;
    private String partenaireNom;
    private LocalDate dateCroisement;
    private LocalDate dateMiseBasEstimee;
    private StatutGestation status;
    private Integer nbPorcelets;
    private String observations;
}


