package tech.steve.essaie.dto;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DashboardStatsDto(long totalAnimaux,
                                long vivants,
                                long morts,
                                long vendus,

                                long totalCroisements,
                                long totalNaissances,
                                long totalOperations,
                                long totalTransactions,

                                BigDecimal totalDepenses,
                                BigDecimal totalRecettes,
                                BigDecimal totalInvestissements,
                                BigDecimal soldeNet
) { }
