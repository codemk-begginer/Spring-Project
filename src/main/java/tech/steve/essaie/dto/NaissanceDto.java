package tech.steve.essaie.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;
import java.util.UUID;


public record NaissanceDto(
                           UUID id,
                           UUID croisementId,

                           @NotNull(message = "La date de naissance est obligatoire")
                           LocalDate dateNaissance,

                           @NotNull(message = "Le nombre de porcelets est obligatoire")
                           @Min(value = 1, message = "Il doit y avoir au moins 1 porcelet")
                           Integer nbVivant,

                           Integer nbMort,

                           @NotBlank(message = "Le nom de l’intervenant est requis")
                           String nomIntervenant,

                           @NotBlank(message = "Le téléphone de l’intervenant est requis")
                           String telephoneIntervenant,

                           String observations,


                           UUID fermeId) {

}