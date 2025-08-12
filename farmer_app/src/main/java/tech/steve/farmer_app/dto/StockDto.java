package tech.steve.farmer_app.dto;

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
public class StockDto {

    private Long id;

    @NotBlank(message = "Le nom du produit est requis")
    private String produit;

    private String description;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantite;

    private LocalDate dateAjout;

    private String fournisseur;

    private String categorie;

    private String nomIntervenant;

    private String telephoneIntervenant;

    private Long fermeId;
}