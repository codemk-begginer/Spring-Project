package tech.steve.essaie.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String message;

    private LocalDate date;

    private boolean lue;

    private String status; // Exemple : ACTIF, TRAITÉ, ARCHIVÉ

    @ManyToOne
    private Animal animal;

    @ManyToOne(optional = false)
    private Ferme ferme;

}
