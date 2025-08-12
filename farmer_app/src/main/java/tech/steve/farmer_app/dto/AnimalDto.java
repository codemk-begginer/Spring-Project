package tech.steve.farmer_app.dto;

import com.porc.enums.Sexe;
import com.porc.enums.StatutAnimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {

    private Long id;
    private String code;
    private LocalDate dateNaissance;
    private LocalDate dateMort;
    private Sexe sexe;
    private StatutAnimal statut;
    private Integer generation;

    private Long mereId;
    private Long pereId;

    private String observations;
    private String qrCodeUrl;

    private Long fermeId;
}

