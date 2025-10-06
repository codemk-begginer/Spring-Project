package tech.steve.essaie.repository;

import tech.steve.essaie.enums.TypeOperation;
import tech.steve.essaie.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {

     List<Operation> findByFermeId(UUID fermeId);

     List<Operation> findByAnimalId(UUID animalId);

     List<Operation> findByType(TypeOperation type);

     List<Operation> findByDateBetween(LocalDate start, LocalDate end);


     List<Operation> findByProduitContainingIgnoreCaseOrObservationsContainingIgnoreCase(String keyword, String key);

     List<Operation> findLastDateByAnimalIdAndType(UUID id, TypeOperation operation);


}
