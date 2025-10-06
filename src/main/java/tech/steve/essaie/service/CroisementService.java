package tech.steve.essaie.service;

import tech.steve.essaie.dto.CroisementDto;
import tech.steve.essaie.dto.CroisementHistoriqueDto;
import tech.steve.essaie.enums.StatutGestation;
import tech.steve.essaie.model.HistoriqueModification;

import java.util.List;
import java.util.UUID;

public interface CroisementService {
    CroisementDto create(CroisementDto dto);
    CroisementDto update(UUID id, CroisementDto dto);
    void delete(UUID id);
    CroisementDto findById(UUID id);
    List<CroisementDto> findAll();
    List<CroisementDto> findByMere(UUID mereId);
    List<CroisementDto> findByPere(UUID pereId);
    List<CroisementDto> findByStatus(StatutGestation status);
    List<CroisementHistoriqueDto> getHistoriqueCroisementsParAnimal(UUID animalId);
    List<HistoriqueModification> getHistoriqueModifications(UUID croisementId);
}