package tech.steve.essaie.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaissanceDto {

    private Long id;

    private Long croisementId;

    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

    @NotNull(message = "Le nombre de porcelets est obligatoire")
    @Min(value = 1, message = "Il doit y avoir au moins 1 porcelet")
    private Integer nbVivant;

    private Integer nbMort;

    @NotBlank(message = "Le nom de l’intervenant est requis")
    private String nomIntervenant;

    @NotBlank(message = "Le téléphone de l’intervenant est requis")
    private String telephoneIntervenant;

    private String observations;


    private Long fermeId;
}