package tech.steve.essaie.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jwt")
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String valeur;
    private boolean desactive;
    private boolean expire;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private RefreshToken refreshToken;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;


}
