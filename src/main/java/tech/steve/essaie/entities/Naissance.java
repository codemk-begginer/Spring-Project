package tech.steve.essaie.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Naissance {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

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
