package com.porc.service.impl;


import com.porc.dto.AnimalDto;
import com.porc.enums.StatutAnimal;
import com.porc.enums.Sexe;
import com.porc.exception.NotFoundException;
import com.porc.mapper.AnimalMapper;
import com.porc.model.Animal;
import com.porc.model.Ferme;
import com.porc.repository.AnimalRepository;
import com.porc.repository.FermeRepository;
import com.porc.service.AnimalService;
import com.porc.service.HistoriqueModificationService;
import com.porc.service.QrCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

        // Génération QR Code
        String qrCodeUrl = qrCodeService.genererQRCodePourAnimal(animal);
        animal.setQrCodeUrl(qrCodeUrl);

        // Gérération
        animal.setGeneration(calculerGeneration(animal));

        Animal saved = animalRepository.save(animal);
        return animalMapper.toDto(saved);
    }

    @Override
    public AnimalDto update(Long id, AnimalDto dto) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal non trouvé avec l'ID : " + id));

        Animal beforeUpdate = new Animal(animal); // Copie pour historique
        animalMapper.updateEntityFromDto(dto, animal);

        if (dto.getFermeId() != null) {
            Ferme ferme = fermeRepository.findById(dto.getFermeId())
                    .orElseThrow(() -> new NotFoundException("Ferme non trouvée"));
            animal.setFerme(ferme);
        }

        animal.setGeneration(calculerGeneration(animal));
        Animal updated = animalRepository.save(animal);

        // Historisation des changements
        historiqueService.enregistrerModifications("Animal", id, beforeUpdate, updated);

        return animalMapper.toDto(updated);
    }

    @Override
    public AnimalDto findById(Long id) {
        return animalRepository.findById(id)
                .map(animalMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Animal non trouvé avec l'ID : " + id));
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
        return animalRepository.findByFermeIdAndSexe(fermeId, sexe)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findByMere(Long mereId) {
        return animalRepository.findByMereId(mereId)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findByPere(Long pereId) {
        return animalRepository.findByPereId(pereId)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findDescendance(Long animalId) {
        List<AnimalDto> descendance = new ArrayList<>();
        List<Animal> enfants = animalRepository.findByMereIdOrPereId(animalId, animalId);
        for (Animal enfant : enfants) {
            descendance.add(animalMapper.toDto(enfant));
            descendance.addAll(findDescendance(enfant.getId()));
        }
        return descendance;
    }

    private int calculerGeneration(Animal animal) {
        int generationMere = Optional.ofNullable(animal.getMere())
                .map(Animal::getGeneration).orElse(0);
        int generationPere = Optional.ofNullable(animal.getPere())
                .map(Animal::getGeneration).orElse(0);
        return Math.max(generationMere, generationPere) + 1;
    }
}