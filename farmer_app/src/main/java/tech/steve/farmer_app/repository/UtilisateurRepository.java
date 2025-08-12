package tech.steve.farmer_app.repository;

import org.springframework.data.repository.CrudRepository;
import tech.steve.farmer_app.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByEmail(String email);
}