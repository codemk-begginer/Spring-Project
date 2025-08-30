package tech.steve.essaie.dto;


import tech.steve.essaie.enums.StatutGestation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CroisementDto {

    private Long id;

    private Long mereId;
    private Long pereId;

    private String nomMereExterne;
    private String nomPereExterne;

    @NotNull(message = "La date de croisement est obligatoire")
    @PastOrPresent(message = "La date de croisement ne peut pas être dans le futur")
    private LocalDate dateCroisement;

    private LocalDate dateMiseBasEstimee;

    @NotNull(message = "Le statut de la gestation est obligatoire")
    private StatutGestation status;

    @Min(value = 0, message = "Le nombre de porcelets ne peut pas être négatif")
    private Integer nbPorcelets;

    @NotBlank(message = "Le nom de l’intervenant est requis")
    private String nomIntervenant;

    @NotBlank(message = "Le téléphone de l’intervenant est requis")
    private String telephoneIntervenant;

    private String observations;

    private Long fermeId;
}