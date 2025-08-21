package tech.steve.essaie.model;

import tech.steve.essaie.enums.StatutGestation;
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
public class Croisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Animal mere;

    @ManyToOne
    private Animal pere;

    private String nomMereExterne;
    private String nomPereExterne;

    private LocalDate dateCroisement;
    private LocalDate dateMiseBasEstimee;

    @Enumerated(EnumType.STRING)
    private StatutGestation status;

    private Integer nbPorcelets;
    private String nomIntervenant;
    private String telephoneIntervenant;
    private String observations;

    @ManyToOne(optional = false)
    private Ferme ferme;

}



