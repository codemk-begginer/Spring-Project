package tech.steve.essaie.service;

import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.AnimalGenealogieDto;
import tech.steve.essaie.enums.Sexe;

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

    List<AnimalDto> findAllByFerme(Long fermeId);

    void archiver(Long id);

    List<AnimalDto> findBySexe(Long fermeId, Sexe sexe);

    List<AnimalDto> findByMere(Long mereId);

    List<AnimalDto> findByPere(Long pereId);

    List<AnimalDto> findDescendance(Long animalId);
}

