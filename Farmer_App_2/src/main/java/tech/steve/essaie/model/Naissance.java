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
public class Naissance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nbVivant;
    private Integer nbMort;
    private LocalDate dateNaissance;

    @ManyToOne
    private Croisement croisement;

    private String observations;
    private String nomIntervenant;
    private String telephoneIntervenant;

    @ManyToOne(optional = false)
    private Ferme ferme;

}