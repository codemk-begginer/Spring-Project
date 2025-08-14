package tech.steve.essaie.service.impl;


import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.AnimalGenealogieDto;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.AnimalMapper;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.repository.AnimalRepository;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.service.AnimalService;
import tech.steve.essaie.service.HistoriqueModificationService;
import tech.steve.essaie.service.QrCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final QrCodeService qrCodeService;
    private final FermeRepository fermeRepository;
    private final HistoriqueModificationService historiqueService;

    @Override
    @Transactional
    public AnimalDto create(AnimalDto dto) {
        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.getFermeId()));

        Animal animal = animalMapper.toEntity(dto);
        animal.setFerme(ferme);
        animal.setStatut(StatutAnimal.VIVANT);
        animal.setDateNaissance(LocalDate.now());
        animal.setDateMort(LocalDate.now().plusMonths(12));

        // Génération QR Code
        String qrCodeUrl = qrCodeService.genererQrCodePourAnimal(animal);
        animal.setQrCodeUrl(qrCodeUrl);

        // Gérération
        animal.setGeneration(calculerGeneration(animal));

        Animal saved = animalRepository.save(animal);
        return animalMapper.toDto(saved);
    }

    @Override
    public AnimalDto update(Long id, AnimalDto dto) {
        return null;
    }

//    @Override
//    public AnimalDto update(Long id, AnimalDto dto) {
//        Animal animal = animalRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Animal non trouvé avec l'ID : " + id));
//
//        Animal beforeUpdate = new Animal(animal); // Copie pour historique
//        animalMapper.updateEntityFromDto(dto, animal);
//
//        if (dto.getFermeId() != null) {
//            Ferme ferme = fermeRepository.findById(dto.getFermeId())
//                    .orElseThrow(() -> new NotFoundException("Ferme non trouvée"));
//            animal.setFerme(ferme);
//        }
//
//        animal.setGeneration(calculerGeneration(animal));
//        Animal updated = animalRepository.save(animal);
//
//        // Historisation des changements
//        historiqueService.enregistrerModification("Animal", id, beforeUpdate, updated);
//
//        return animalMapper.toDto(updated);
//    }

    @Override
    public void archive(Long id) {

    }

    @Override
    public AnimalDto findById(Long id) {
        return animalRepository.findById(id)
                .map(animalMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Animal non trouvé avec l'ID : " + id));
    }

    @Override
    public List<AnimalDto> findAll() {
        return List.of();
    }

    @Override
    public List<AnimalDto> findBySexe(String sexe) {
        return List.of();
    }

    @Override
    public List<AnimalDto> findByStatut(String statut) {
        return List.of();
    }

    @Override
    public List<AnimalDto> findByGeneration(Integer generation) {
        return List.of();
    }

    @Override
    public AnimalGenealogieDto getArbreGenealogique(Long animalId) {
        return null;
    }

    @Override
    public List<AnimalDto> getDescendants(Long animalId) {
        return List.of();
    }

    @Override
    public AnimalDto getByQrCode(String qrCode) {
        return null;
    }

    @Override
    public List<AnimalDto> findAllByFerme(Long fermeId) {
        return animalRepository.findByFermeId(fermeId)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void archiver(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal non trouvé avec l'ID : " + id));
        animal.setStatut(StatutAnimal.ARCHIVE);
        animalRepository.save(animal);
    }

    @Override
    public List<AnimalDto> findBySexe(Long fermeId, Sexe sexe) {
        return List.of();
    }

    @Override
    public List<AnimalDto> findByMere(Long mereId) {
        return List.of();
    }

    @Override
    public List<AnimalDto> findByPere(Long pereId) {
        return List.of();
    }

    @Override
    public List<AnimalDto> findDescendance(Long animalId) {
        return List.of();
    }

//    @Override
//    public List<AnimalDto> findBySexe(Long fermeId, Sexe sexe) {
//        return animalRepository.findByFermeIdAndSexe(fermeId, sexe)
//                .stream().map(animalMapper::toDto)
//                .collect(Collectors.toList());
//    }

//    @Override
//    public List<AnimalDto> findByMere(Long mereId) {
//        return animalRepository.findByMereId(mereId)
//                .stream().map(animalMapper::toDto)
//                .collect(Collectors.toList());
//    }

//    @Override
//    public List<AnimalDto> findByPere(Long pereId) {
//        return animalRepository.findByPereId(pereId)
//                .stream().map(animalMapper::toDto)
//                .collect(Collectors.toList());
//    }

//    @Override
//    public List<AnimalDto> findDescendance(Long animalId) {
//        List<AnimalDto> descendance = new ArrayList<>();
//        List<Animal> enfants = animalRepository.findByMereIdOrPereId(animalId, animalId);
//        for (Animal enfant : enfants) {
//            descendance.add(animalMapper.toDto(enfant));
//            descendance.addAll(findDescendance(enfant.getId()));
//        }
//        return descendance;
//    }

    private int calculerGeneration(Animal animal) {
        int generationMere = Optional.ofNullable(animal.getMere())
                .map(Animal::getGeneration).orElse(0);
        int generationPere = Optional.ofNullable(animal.getPere())
                .map(Animal::getGeneration).orElse(0);
        return Math.max(generationMere, generationPere) + 1;
    }
}