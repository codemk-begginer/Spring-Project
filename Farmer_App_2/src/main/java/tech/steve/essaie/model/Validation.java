package tech.steve.essaie.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Instant creation;
    private Instant expiration;
    private Instant activation = Instant.now();
    private String code;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH})
    private Utilisateur utilisateur;


}
