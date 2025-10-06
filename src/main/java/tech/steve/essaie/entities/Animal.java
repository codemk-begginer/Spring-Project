package tech.steve.essaie.entities;

import org.hibernate.annotations.GenericGenerator;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
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
public class Animal {

    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;



    private LocalDate dateNaissance;
    private LocalDate dateMort;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @Enumerated(EnumType.STRING)
    private StatutAnimal statut;

    private String code;

    private String nom;

    @Column(name = "qr_code_url", columnDefinition = "TEXT")
    private String qrCodeUrl;

    @ManyToOne
    private Animal verrat;

    @ManyToOne
    private Animal truie;

    private Integer generation;

    private String observations;

    @ManyToOne(optional = false)
    private Ferme ferme;

}
