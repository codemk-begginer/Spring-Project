package tech.steve.essaie.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh-token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean expire;
    private String valeur;
    private Instant creation;
    private Instant expiration;
}
