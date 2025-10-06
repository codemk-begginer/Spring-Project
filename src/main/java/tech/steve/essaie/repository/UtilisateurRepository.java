package tech.steve.essaie.repository;

import tech.steve.essaie.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {

    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByFermeId(UUID fermeId);

    List<Utilisateur> findByActif(boolean actif);

    Optional<Utilisateur> findByIdAndFermeId(UUID id, UUID fermeId);
}
