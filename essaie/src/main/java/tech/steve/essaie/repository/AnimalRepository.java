package com.porc.repository;

import com.porc.enums.Sexe;
import com.porc.enums.StatutAnimal;
import com.porc.model.Animal;
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

}
