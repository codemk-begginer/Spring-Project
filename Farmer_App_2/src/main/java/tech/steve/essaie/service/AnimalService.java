package tech.steve.essaie.service;

import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.AnimalGenealogieDto;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
import tech.steve.essaie.model.Animal;

import java.util.List;


public interface AnimalService {
    AnimalDto create(AnimalDto dto);

    List<AnimalDto> findAll();

    AnimalDto update(Long id, AnimalDto dto);
    void archive(Long id);
    AnimalDto findById(Long id);
    public List<AnimalDto> findAllByFerme(Long fermeId);
    public List<AnimalDto> findByStatut(StatutAnimal statut);
    List<AnimalDto> findByGeneration(Integer generation);
    AnimalGenealogieDto getArbreGenealogique(Long animalId);
    List<AnimalDto> getDescendants(Long animalId);
    AnimalDto getByQrCode(String qrCode);

    public List<AnimalDto> findByMere(Animal mereId);
    public List<AnimalDto> findByPere(Animal pereId);
    public List<AnimalDto> findBySexe(Sexe sexe);
}

