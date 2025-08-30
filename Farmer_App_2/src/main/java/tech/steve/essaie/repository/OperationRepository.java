package tech.steve.essaie.repository;

import org.springframework.data.domain.Page;
import tech.steve.essaie.enums.TypeOperation;
import tech.steve.essaie.mapper.NaissanceMapper;
import tech.steve.essaie.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

     List<Operation> findByFermeId(Long fermeId);

     List<Operation> findByAnimalId(Long animalId);

     List<Operation> findByType(TypeOperation type);

     List<Operation> findByDateBetween(LocalDate start, LocalDate end);

//     LocalDate findLastDateByAnimalAndType(Long id,TypeOperation type);

//     LocalDate findLastDateByAnimal(Long id);

     List<Operation> findByProduitContainingIgnoreCaseOrObservationsContainingIgnoreCase(String keyword, String key);




}
