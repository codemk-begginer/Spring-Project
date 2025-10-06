package tech.steve.essaie.dto;

import tech.steve.essaie.enums.TypeOperation;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


public record OperationDto(UUID animalId,

                           @NotNull(message = "La date est obligatoire")
                           LocalDate date,

                           LocalDateTime horodatage,

                           @NotNull(message = "Le type d’opération est obligatoire")
                           TypeOperation type,

                           String produit,
                           String observations,
                           Double dose,
                           BigDecimal cout,

                           String nomIntervenant,
                           String telephoneIntervenant,

                           UUID fermeId) {

    
}