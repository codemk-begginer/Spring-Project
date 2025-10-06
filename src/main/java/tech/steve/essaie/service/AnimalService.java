package tech.steve.essaie.service;

import tech.steve.essaie.dto.animal.AnimalDto;
import tech.steve.essaie.dto.AnimalGenealogieDto;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
import tech.steve.essaie.model.Animal;

import java.util.List;
import java.util.UUID;


public interface AnimalService {
    AnimalDto create(AnimalDto dto);

    List<AnimalDto> findAll();

    AnimalDto update(UUID id, AnimalDto dto);
    void archive(UUID id);
    AnimalDto findById(UUID id);
    public List<AnimalDto> findAllByFerme(UUID fermeId);
    public List<AnimalDto> findByStatut(StatutAnimal statut);
    List<AnimalDto> findByGeneration(Integer generation);
    AnimalGenealogieDto getArbreGenealogique(UUID animalId);
    List<AnimalDto> getDescendants(UUID animalId);
    AnimalDto getByQrCode(String qrCode);

    public List<AnimalDto> findByMere(Animal mereId);
    public List<AnimalDto> findByPere(Animal pereId);
    public List<AnimalDto> findBySexe(Sexe sexe);
}

