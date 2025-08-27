package tech.steve.essaie.repository;

import org.springframework.data.jpa.repository.Query;
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
//
//    List<Animal> findByStatutAndFermeId(StatutAnimal statut, Long fermeId);
//
//    List<Animal> findBySexeAndFermeId(Sexe sexe, Long fermeId);
//
//    Optional<Animal> findByIdAndFermeId(Long id, Long fermeId);

    List<Animal> findByMere(Animal mereId);

    List<Animal> findByPere(Animal mereId);

    List<Animal> findBySexe(Sexe sexe);

    List<Animal> findByStatut(StatutAnimal statutAnimal);

@Query("SELECT a.generation FROM Animal a WHERE a.id = :id ")
    List<Integer>findGenerationById(Long id);

@Query("SELECT a.qrCodeUrl FROM Animal a WHERE a.id = :id ")
    List<String>findQrCodeUrlById(Long id);


}
