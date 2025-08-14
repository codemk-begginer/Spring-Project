package com.porc.service;

import com.porc.dto.AnimalDto;
import com.porc.dto.AnimalGenealogieDto;
import com.porc.model.HistoriqueModification;

import java.util.List;


public interface AnimalService {
    AnimalDto create(AnimalDto dto);
    AnimalDto update(Long id, AnimalDto dto);
    void archive(Long id);
    AnimalDto findById(Long id);
    List<AnimalDto> findAll();
    List<AnimalDto> findBySexe(String sexe);
    List<AnimalDto> findByStatut(String statut);
    List<AnimalDto> findByGeneration(Integer generation);
    AnimalGenealogieDto getArbreGenealogique(Long animalId);
    List<AnimalDto> getDescendants(Long animalId);
    AnimalDto getByQrCode(String qrCode);
}

