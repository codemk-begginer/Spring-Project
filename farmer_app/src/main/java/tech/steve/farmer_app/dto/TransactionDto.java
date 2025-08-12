package tech.steve.farmer_app.dto;

import com.porc.enums.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;

    private TypeTransaction type;

    private String categorie;

    private BigDecimal montant;

    private LocalDate date;

    private String description;

    private Long animalId;

    private Integer generation;

    private String nomIntervenant;

    private String telephoneIntervenant;

    private LocalDateTime horodatage;

    private Long fermeId;
}