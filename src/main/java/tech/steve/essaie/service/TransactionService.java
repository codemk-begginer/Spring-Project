package tech.steve.essaie.service;

import tech.steve.essaie.dto.TransactionDto;
import tech.steve.essaie.enums.TypeTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionDto create(TransactionDto dto);
    TransactionDto update(UUID id, TransactionDto dto);
    void delete(UUID id);
    TransactionDto findById(UUID id);
    List<TransactionDto> findAll();
    List<TransactionDto> findByAnimalId(UUID animalId);
    List<TransactionDto> findByType(TypeTransaction type);

    double getTotalByType(String type);
    double getTotalForAnimal(UUID animalId);
    double getTotalGlobal();
    List<String> getHistoriqueTransaction(UUID transactionId);

    public List<TransactionDto> findByDateRange(LocalDate startDate, LocalDate endDate);

    public List<TransactionDto> findByIntervenant(String intervenant);
}
