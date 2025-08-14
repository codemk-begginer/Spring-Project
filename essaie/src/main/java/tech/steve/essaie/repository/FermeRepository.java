package com.porc.repository;

import com.porc.model.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FermeRepository extends JpaRepository<Ferme, Long> {
    Optional<Ferme> findByNomIgnoreCase(String nom);
}
