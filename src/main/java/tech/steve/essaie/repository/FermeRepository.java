package tech.steve.essaie.repository;

import tech.steve.essaie.model.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FermeRepository extends JpaRepository<Ferme, UUID> {
    Optional<Ferme> findByNomIgnoreCase(String nom);
}
