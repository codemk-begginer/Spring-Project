package tech.steve.farmer_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private long totalAnimaux;
    private long vivants;
    private long morts;
    private long vendus;

    private long totalCroisements;
    private long totalNaissances;
    private long totalOperations;
    private long totalTransactions;

    private BigDecimal totalDepenses;
    private BigDecimal totalRecettes;
    private BigDecimal totalInvestissements;
    private BigDecimal soldeNet;
}
