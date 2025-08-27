package tech.steve.essaie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.steve.essaie.model.Ferme;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {

    private Long id;

    @NotBlank(message = "Le nom du produit est requis")
    private String produit;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantite;

    private String unite;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate Horodatage;


    private String typeMouvement;

    private String description;

    private String categorie;


    private String fournisseur;


    private String nomIntervenant;

    private String telephoneIntervenant;

    private int SeuilAlerte = 1 ;

    private Long fermeId;


}