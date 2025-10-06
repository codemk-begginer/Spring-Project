package tech.steve.essaie.dto.animal;

import jakarta.persistence.Column;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;


import java.time.LocalDate;
import java.util.UUID;


public record AnimalDto( UUID truie,
                         UUID verrat,
                         LocalDate dateNaissance,
                         Sexe sexe,
                         StatutAnimal statut,
                         String observations,
                         UUID fermeId) {


}

