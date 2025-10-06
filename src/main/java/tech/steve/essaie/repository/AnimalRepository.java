package tech.steve.essaie.repository;

import org.springframework.data.jpa.repository.Query;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
import tech.steve.essaie.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, UUID> {

    List<Animal> findByFermeId(UUID fermeId);


    List<Animal> findByTruie(Animal mereId);

    List<Animal> findByVerrat(Animal mereId);

    List<Animal> findBySexe(Sexe sexe);

    List<Animal> findByStatut(StatutAnimal statutAnimal);

@Query("SELECT a.generation FROM Animal a WHERE a.id = :id ")
    List<Integer>findGenerationById(UUID id);

@Query("SELECT a.qrCodeUrl FROM Animal a WHERE a.id = :id ")
    List<String>findQrCodeUrlById(UUID id);

    List<Long> countByStatut(StatutAnimal status);


}
