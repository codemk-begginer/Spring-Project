package tech.steve.essaie.repository;


import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Naissance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NaissanceRepository extends JpaRepository<Naissance, UUID> {

    List<Naissance> findByFermeId(UUID fermeId);


    Optional<Naissance> findByIdAndFermeId(UUID id, UUID fermeId);

    List<Naissance> findByDateNaissance(LocalDate date);
}
