package tech.steve.essaie.service.impl;


import lombok.extern.slf4j.Slf4j;
import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.AnimalGenealogieDto;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.AnimalMapper;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.model.Role;
import tech.steve.essaie.model.Utilisateur;
import tech.steve.essaie.repository.AnimalRepository;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.service.AnimalService;
import tech.steve.essaie.service.HistoriqueModificationService;
import tech.steve.essaie.service.QrCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
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


        //generation d'un code unique

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d",randomInteger);



        Animal animal = animalMapper.toEntity(dto);
        animal.setFerme(ferme);
        if (dto.getMereId() != null) {
            animal.setMere(animalRepository.findById(dto.getMereId())
                    .orElseThrow(() -> new RuntimeException("Mère introuvable")));
        }

        if (dto.getPereId() != null) {
            animal.setPere(animalRepository.findById(dto.getPereId())
                    .orElseThrow(() -> new RuntimeException("Père introuvable")));
        }
        animal.setCode(code);
        animal.setStatut(StatutAnimal.VIVANT);
        animal.setDateNaissance(LocalDate.now());
        animal.setDateMort(LocalDate.now().plusMonths(24));
        animal.setNom("porc" + code);




        // Génération QR Code
        String qrCodeUrl = qrCodeService.genererQrCodePourAnimal(code);
        animal.setQrCodeUrl(qrCodeUrl);


        // Gérération de l'animal

        List<Integer> generationPere = animalRepository.findGenerationById(dto.getPereId());
        List<Integer> generationMere = animalRepository.findGenerationById(dto.getMereId());

        if (!generationPere.isEmpty() || !generationMere.isEmpty() ) { // Vérifie si la liste n'est pas vide
            int result = Math.max(generationMere.get(0),generationPere.get(0));
            animal.setGeneration(result + 1) ;
        } else {
            animal.setGeneration(1);
        }


        Animal saved = animalRepository.save(animal);
        return animalMapper.toDto(saved);
    }

    @Override
    public AnimalDto findById(Long id) {
        return animalRepository.findById(id)
                .map(animalMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Animal non trouvé avec l'ID : " + id));
    }

    @Override
    public List<AnimalDto> findAll() {
        return animalRepository.findAll().stream()
                .map(animalMapper::toDto)
                .toList();
    }




    @Override
    public AnimalDto update(Long id, AnimalDto dto) {

        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal non trouvé avec id : " + id));

        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.getFermeId()));
        animal.setFerme(ferme);


        if (dto.getMereId() != null) {
            animal.setMere(animalRepository.findById(dto.getMereId())
                    .orElseThrow(() -> new RuntimeException("Mère introuvable")));
        }

        if (dto.getPereId() != null) {
            animal.setPere(animalRepository.findById(dto.getPereId())
                    .orElseThrow(() -> new RuntimeException("Père introuvable")));
        }

        //mise a jour de la generation

            List<Integer> generationPere = animalRepository.findGenerationById(dto.getPereId());
            List<Integer> generationMere = animalRepository.findGenerationById(dto.getMereId());

            if (!generationPere.isEmpty() || !generationMere.isEmpty() ) { // Vérifie si la liste n'est pas vide
                int result = Math.max(generationMere.get(0),generationPere.get(0));
                animal.setGeneration(result + 1) ;
            }

        if (dto.getDateNaissance() != null){
            animal.setDateMort(dto.getDateNaissance().plusMonths(24));
        }

        animal.setObservations(dto.getObservations());
        animal.setSexe(dto.getSexe());
        animal.setStatut(dto.getStatut());



        animal  = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }



    @Override
    public void archive(Long id) {
                Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal non trouvé avec l'ID : " + id));
        animal.setStatut(StatutAnimal.ARCHIVE);
        animalRepository.save(animal);

    }


    @Override
    public List<AnimalDto> findAllByFerme(Long fermeId) {
                return animalRepository.findByFermeId(fermeId)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findByMere(Animal mereId) {
        return animalRepository.findByMere(mereId)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findByPere(Animal pereId) {
        return animalRepository.findByPere(pereId)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findBySexe(Sexe sexe) {
        return animalRepository.findBySexe(sexe)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public List<AnimalDto> findByStatut(StatutAnimal statut) {
        return animalRepository.findByStatut(statut)
                .stream().map(animalMapper::toDto)
                .collect(Collectors.toList());
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




}