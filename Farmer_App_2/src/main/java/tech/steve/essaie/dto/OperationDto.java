package tech.steve.essaie.dto;

import tech.steve.essaie.enums.TypeOperation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {

    private Long id;

    private Long animalId;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    private LocalDateTime horodatage;

    @NotNull(message = "Le type d’opération est obligatoire")
    private TypeOperation type;

    private String produit;
    private String observations;
    private Double dose;
    private BigDecimal cout;

    private String nomIntervenant;
    private String telephoneIntervenant;

    private Long fermeId;
}