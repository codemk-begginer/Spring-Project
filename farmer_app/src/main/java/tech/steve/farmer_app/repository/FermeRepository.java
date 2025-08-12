package tech.steve.farmer_app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.steve.farmer_app.model.Ferme;

import java.util.Optional;

@Repository
public interface FermeRepository extends JpaRepository<Ferme, Long> {
    Optional<Ferme> findByNomIgnoreCase(String nom);
}
