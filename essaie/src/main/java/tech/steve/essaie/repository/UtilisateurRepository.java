package com.porc.repository;

import com.porc.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByFermeId(Long fermeId);

    Optional<Utilisateur> findByIdAndFermeId(Long id, Long fermeId);
}
