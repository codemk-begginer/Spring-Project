package com.porc.repository;

import com.porc.model.HistoriqueModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueModificationRepository extends JpaRepository<HistoriqueModification, Long> {
    List<HistoriqueModification> findByEntiteModifieeAndIdEntite(String entiteModifiee, Long idEntite);
    List<HistoriqueModification> findByEntiteModifieeAndIdEntiteOrderByDateModificationDesc(String entite, Long idEntite);

}
