package com.porc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueModification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entiteModifiee;
    private Long idEntite;
    private String champModifie;
    private String ancienneValeur;
    private String nouvelleValeur;

    private LocalDateTime dateModification;

    private String nomIntervenant;
    private String telephoneIntervenant;

    @ManyToOne
    private Ferme ferme;

}

