package tech.steve.essaie.dto;

import lombok.Builder;
import tech.steve.essaie.enums.StatutGestation;


import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CroisementHistoriqueDto( String role, // "MERE" ou "PERE"
                                       UUID partenaireId,
                                       String partenaireNom,
                                       LocalDate dateCroisement,
                                       LocalDate dateMiseBasEstimee,
                                       StatutGestation status,
                                       Integer nbPorcelets,
                                       String observations) {

}


