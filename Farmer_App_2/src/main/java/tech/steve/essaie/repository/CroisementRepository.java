package tech.steve.essaie.repository;

import tech.steve.essaie.enums.StatutGestation;
import tech.steve.essaie.model.Croisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CroisementRepository extends JpaRepository<Croisement, Long> {

    List<Croisement> findByFermeId(Long fermeId);

    List<Croisement> findByMereIdAndFermeId(Long mereId, Long fermeId);

    List<Croisement> findByMereId(Long mereId);

    List<Croisement> findByPereId(Long mereId);

    List<Croisement> findByStatus(StatutGestation status);

    List<Croisement> findByPereIdAndFermeId(Long pereId, Long fermeId);

    List<Croisement> findByStatusAndFermeId(StatutGestation status, Long fermeId);

    Optional<Croisement> findByIdAndFermeId(Long id, Long fermeId);
}
