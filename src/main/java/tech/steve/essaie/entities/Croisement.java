package tech.steve.essaie.entities;

import org.hibernate.annotations.GenericGenerator;
import tech.steve.essaie.enums.StatutGestation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Croisement {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ManyToOne
    private Animal truie;

    @ManyToOne
    private Animal verrat;

    private String nomTruieExterne;
    private String nomVerratExterne;

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
