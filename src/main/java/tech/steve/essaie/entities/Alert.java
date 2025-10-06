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
public class Alert {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

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