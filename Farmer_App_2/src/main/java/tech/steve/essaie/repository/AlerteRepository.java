package tech.steve.essaie.repository;

import tech.steve.essaie.model.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlerteRepository extends JpaRepository<Alerte, Long> {

    List<Alerte> findByFermeId(Long fermeId);

    List<Alerte> findByAnimalId(Long animalId);

    List<Alerte> findByLueFalse();

    List<Alerte> findByStatus(String status);

    List<Alerte> findByType(String type);

//    List<Alerte> findByDatePrevueBetweenAndFermeId(LocalDate debut, LocalDate fin, Long fermeId);
//
//    List<Alerte> findByAnimalIdAndFermeId(Long animalId, Long fermeId);
//
//    Optional<Alerte> findByIdAndFermeId(Long id, Long fermeId);
}
