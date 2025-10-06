package tech.steve.essaie.entities;

import org.hibernate.annotations.GenericGenerator;
import tech.steve.essaie.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

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