package tech.steve.essaie.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produit;
    private Integer quantite;
    private String unite;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate horodatage;

    private String typeMouvement;

    private String description;
    private String categorie;
    private String fournisseur;

    private String nomIntervenant;
    private String telephoneIntervenant;

    @Column(nullable = true)
    private int SeuilAlerte = 1;

    @ManyToOne
    private Ferme ferme;

}
