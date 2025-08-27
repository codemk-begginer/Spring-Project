package tech.steve.essaie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalGenealogieDto {

    private Long id;
    private String nom;
    private String code;
    private String sexe;
    private String statut;
    private String qrCodeUrl;
    private Integer generation;
    private String dateNaissance;

    // Parents (peuvent être null si inconnus)
    private AnimalGenealogieDto pere;
    private AnimalGenealogieDto mere;

    // Descendance directe
    private List<AnimalGenealogieDto> enfants;
}
