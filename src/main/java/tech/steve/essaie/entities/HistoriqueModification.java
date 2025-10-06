package tech.steve.essaie.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueModification {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String entiteModifiee;
    private UUID idEntite;
    private String champModifie;
    private String ancienneValeur;
    private String nouvelleValeur;

    private LocalDateTime dateModification;

    private String nomIntervenant;
    private String telephoneIntervenant;

    @ManyToOne
    private Ferme ferme;

}
