package tech.steve.essaie.model;

import tech.steve.essaie.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    private String categorie;
    private BigDecimal montant;
    private LocalDate date;
    private String description;

    @ManyToOne
    private Animal animal;

//    private Integer generation;

    private String nomIntervenant;
    private String telephoneIntervenant;

    @ManyToOne(optional = false)
    private Ferme ferme;

}
