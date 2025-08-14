package tech.steve.essaie.repository;

import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
import tech.steve.essaie.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByFermeId(Long fermeId);

    List<Animal> findByStatutAndFermeId(StatutAnimal statut, Long fermeId);

    List<Animal> findBySexeAndFermeId(Sexe sexe, Long fermeId);

    Optional<Animal> findByIdAndFermeId(Long id, Long fermeId);

    Optional<Object> findByFermeIdAndSexe(Long fermeId, Sexe sexe);

    Optional<Object> findByMereId(Long mereId);
}
