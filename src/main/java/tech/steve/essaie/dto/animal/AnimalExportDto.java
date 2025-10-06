package tech.steve.essaie.dto.animal;

import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;

import java.time.LocalDate;
import java.util.UUID;

public record AnimalExportDto(UUID truie,
                              UUID verrat,
                              LocalDate dateNaissance,
                              LocalDate dateMort,
                              Sexe sexe,
                              StatutAnimal statut,
                              Integer generation,
                              String nom,
                              String observations,

                              UUID fermeId) {
}
