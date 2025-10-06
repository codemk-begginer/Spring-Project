package tech.steve.essaie.dto;


import tech.steve.essaie.enums.StatutGestation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


import java.time.LocalDate;
import java.util.UUID;


public record CroisementDto(UUID id,

                            UUID truieId,
                            UUID verratId,

                            String nomTruieExterne,
                            String nomVerratExterne,

                            @NotNull(message = "La date de croisement est obligatoire")
                            @PastOrPresent(message = "La date de croisement ne peut pas être dans le futur")
                            LocalDate dateCroisement,

                            LocalDate dateMiseBasEstimee,

                            @NotNull(message = "Le statut de la gestation est obligatoire")
                            StatutGestation status,

                            @Min(value = 0, message = "Le nombre de porcelets ne peut pas être négatif")
                            Integer nbPorcelets,

                            @NotBlank(message = "Le nom de l’intervenant est requis")
                            String nomIntervenant,

                            @NotBlank(message = "Le téléphone de l’intervenant est requis")
                            String telephoneIntervenant,

                            String observations,

                            UUID fermeId) {


}