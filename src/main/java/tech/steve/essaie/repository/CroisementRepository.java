package tech.steve.essaie.repository;

import tech.steve.essaie.enums.StatutGestation;
import tech.steve.essaie.model.Croisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CroisementRepository extends JpaRepository<Croisement, UUID> {

    List<Croisement> findByFermeId(UUID fermeId);

    List<Croisement> findByTruieIdAndFermeId(UUID mereId, UUID fermeId);

    List<Croisement> findByTruieId(UUID mereId);

    List<Croisement> findByVerratId(UUID mereId);

    List<Croisement> findByStatus(StatutGestation status);

    List<Croisement> findDateCroisementByTruieCode(String code);

    List<Croisement> findByVerratIdAndFermeId(UUID pereId, UUID fermeId);

    List<Croisement> findByStatusAndFermeId(StatutGestation status, UUID fermeId);

    Optional<Croisement> findByIdAndFermeId(UUID id, UUID fermeId);
}
