package tech.steve.essaie.repository;

import tech.steve.essaie.enums.TypeTransaction;
import tech.steve.essaie.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//    List<Transaction> findByFermeId(Long fermeId);
//
    List<Transaction> findByAnimalId(Long animalId);
//
    List<Transaction> findByType(TypeTransaction type);
//
    List<Transaction> findByNomIntervenantIgnoreCase(String nomIntervenant);
//
//
    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);



//    List<Transaction> findByAnimalIdAndFermeId(Long animalId, Long fermeId);
//
//    List<Transaction> findByTypeAndFermeId(TypeTransaction type, Long fermeId);
//
//    List<Transaction> findByDateBetweenAndFermeId(LocalDate start, LocalDate end, Long fermeId);
//
//    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.ferme.id = :fermeId")
//    Double getTotalGlobal(@Param("fermeId") Long fermeId);
//
//    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.type = :type AND t.ferme.id = :fermeId")
//    Double getTotalByType(@Param("type") TypeTransaction type, @Param("fermeId") Long fermeId);
//
//    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.animal.id = :animalId AND t.ferme.id = :fermeId")
//    Double getTotalForAnimal(@Param("animalId") Long animalId, @Param("fermeId") Long fermeId);
//
//    Optional<Transaction> findByIdAndFermeId(Long id, Long fermeId);
}

