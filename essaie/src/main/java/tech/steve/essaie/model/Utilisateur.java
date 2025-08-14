package com.porc.model;

import com.porc.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    private String motDePasse; // Envisager le hash en production

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean actif = true; // Utilisé à la place de suppression

    @ManyToOne
    private Ferme ferme;

}
