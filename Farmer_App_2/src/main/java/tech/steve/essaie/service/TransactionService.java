package tech.steve.essaie.service;

import tech.steve.essaie.dto.TransactionDto;
import tech.steve.essaie.enums.TypeTransaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionDto create(TransactionDto dto);
    TransactionDto update(Long id, TransactionDto dto);
    void delete(Long id);
    TransactionDto findById(Long id);
    List<TransactionDto> findAll();
    List<TransactionDto> findByAnimalId(Long animalId);
    List<TransactionDto> findByType(TypeTransaction type);

    double getTotalByType(String type);
    double getTotalForAnimal(Long animalId);
    double getTotalGlobal();
    List<String> getHistoriqueTransaction(Long transactionId);

    public List<TransactionDto> findByDateRange(LocalDate startDate, LocalDate endDate);

    public List<TransactionDto> findByIntervenant(String intervenant);
}
