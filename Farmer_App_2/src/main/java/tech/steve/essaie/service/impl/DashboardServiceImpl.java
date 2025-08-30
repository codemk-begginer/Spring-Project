package tech.steve.essaie.service.impl;

import lombok.extern.slf4j.Slf4j;
import tech.steve.essaie.dto.DashboardStatsDto;
import tech.steve.essaie.enums.StatutAnimal;
import tech.steve.essaie.enums.TypeTransaction;
import tech.steve.essaie.repository.*;
import tech.steve.essaie.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final AnimalRepository animalRepository;
    private final CroisementRepository croisementRepository;
    private final NaissanceRepository naissanceRepository;
    private final OperationRepository operationRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public DashboardStatsDto getStatistiquesGlobales() {
        long totalAnimaux = animalRepository.count();
        List<Long> vivants =  animalRepository.countByStatut(StatutAnimal.VIVANT);
        List<Long> morts = animalRepository.countByStatut(StatutAnimal.MORT);
        List<Long> vendus = animalRepository.countByStatut(StatutAnimal.VENDU);

        long totalCroisements = croisementRepository.count();
        long totalNaissances = naissanceRepository.count();
        long totalOperations = operationRepository.count();
        long totalTransactions = transactionRepository.count();

        List<BigDecimal> totalDepenses = transactionRepository.countByType(TypeTransaction.DEPENSE);
        List<BigDecimal> totalRecettes = transactionRepository.countByType(TypeTransaction.RECETTE);
        List<BigDecimal> totalInvestissements = transactionRepository.countByType(TypeTransaction.INVESTISSEMENT);

        BigDecimal soldeNet = totalRecettes.get(0).min(totalDepenses.get(0));

        return DashboardStatsDto.builder()
                .totalAnimaux(totalAnimaux)
                .vivants(vivants.get(0))
                .morts(morts.get(0))
                .vendus(vendus.get(0))
                .totalCroisements(totalCroisements)
                .totalNaissances(totalNaissances)
                .totalOperations(totalOperations)
                .totalTransactions(totalTransactions)
                .totalDepenses(totalDepenses.get(0))
                .totalRecettes(totalRecettes.get(0))
                .totalInvestissements(totalInvestissements.get(0))
                .soldeNet(soldeNet)
                .build();
    }
}
