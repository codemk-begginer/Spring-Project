package tech.steve.essaie.repository;

import tech.steve.essaie.model.HistoriqueModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoriqueModificationRepository extends JpaRepository<HistoriqueModification, UUID> {
    List<HistoriqueModification> findByEntiteModifieeAndIdEntite(String entiteModifiee, UUID idEntite);
    List<HistoriqueModification> findByEntiteModifieeAndIdEntiteOrderByDateModificationDesc(String entite, UUID idEntite);

}
