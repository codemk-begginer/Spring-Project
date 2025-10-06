package tech.steve.essaie.service;

import tech.steve.essaie.dto.ferme.FermeDto;
import tech.steve.essaie.dto.ferme.FermeUpdateDto;

import java.util.List;
import java.util.UUID;

public interface FermeService {
    FermeDto create(FermeDto dto);
    FermeUpdateDto update(UUID id, FermeUpdateDto dto);
    void delete(UUID id);
    FermeDto findById(UUID id);
    List<FermeDto> findAll();
    List<FermeDto> findByNom(String nom);
    void assignerUtilisateur(UUID fermeId, UUID utilisateurId);
}

