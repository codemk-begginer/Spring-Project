package tech.steve.essaie.service;

import tech.steve.essaie.dto.CroisementDto;
import tech.steve.essaie.dto.CroisementHistoriqueDto;
import tech.steve.essaie.enums.StatutGestation;
import tech.steve.essaie.model.HistoriqueModification;

import java.util.List;

public interface CroisementService {
    CroisementDto create(CroisementDto dto);
    CroisementDto update(Long id, CroisementDto dto);
    void delete(Long id);
    CroisementDto findById(Long id);
    List<CroisementDto> findAll();
    List<CroisementDto> findByMere(Long mereId);
    List<CroisementDto> findByPere(Long pereId);
    List<CroisementDto> findByStatus(StatutGestation status);
    List<CroisementHistoriqueDto> getHistoriqueCroisementsParAnimal(Long animalId);
    List<HistoriqueModification> getHistoriqueModifications(Long croisementId);
}