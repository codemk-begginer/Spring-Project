package tech.steve.essaie.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jwt")
public class Jwt {

    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    private String valeur;
    private boolean desactive;
    private boolean expire;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private RefreshToken refreshToken;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;


}
