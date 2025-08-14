package tech.chilo.avis.entite;

import jakarta.persistence.*;
import lombok.*;
import tech.chilo.avis.enums.TypeDeRole;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TypeDeRole libelle;

}
