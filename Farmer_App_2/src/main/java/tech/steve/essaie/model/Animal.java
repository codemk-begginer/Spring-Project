package tech.steve.essaie.model;

import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
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
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



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
    private Animal mere;

    @ManyToOne
    private Animal pere;

    private Integer generation;

    private String observations;

    @ManyToOne(optional = false)
    private Ferme ferme;

}
