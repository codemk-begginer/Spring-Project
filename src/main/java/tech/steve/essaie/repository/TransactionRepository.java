package tech.steve.essaie.repository;

import tech.steve.essaie.enums.TypeTransaction;
import tech.steve.essaie.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {



    List<Transaction> findByAnimalId(UUID animalId);

    List<Transaction> findByType(TypeTransaction type);

    List<Transaction> findByNomIntervenantIgnoreCase(String nomIntervenant);

    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<BigDecimal> countByType(TypeTransaction typeTransaction);




}

