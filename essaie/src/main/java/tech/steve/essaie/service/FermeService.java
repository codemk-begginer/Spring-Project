package com.porc.service;

import com.porc.dto.FermeDto;

import java.util.List;

public interface FermeService {
    FermeDto create(FermeDto dto);
    FermeDto update(Long id, FermeDto dto);
    void delete(Long id);
    FermeDto findById(Long id);
    List<FermeDto> findAll();
    List<FermeDto> findByNom(String nom);
    void assignerUtilisateur(Long fermeId, Long utilisateurId);
}

