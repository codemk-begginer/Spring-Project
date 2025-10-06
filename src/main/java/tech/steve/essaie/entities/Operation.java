package tech.steve.essaie.entities;

import org.hibernate.annotations.GenericGenerator;
import tech.steve.essaie.enums.TypeOperation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String produit;
    private double dose;
    private BigDecimal cout;

    private String nomIntervenant;
    private String telephoneIntervenant;

    private String observations;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TypeOperation type;

    private LocalDateTime horodatage;

    @ManyToOne
    private Animal animal;

    @ManyToOne(optional = false)
    private Ferme ferme;

}
