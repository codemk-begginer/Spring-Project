package tech.steve.essaie.service.impl;

import tech.steve.essaie.dto.NaissanceDto;
import tech.steve.essaie.enums.Sexe;
import tech.steve.essaie.enums.StatutAnimal;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.AnimalMapper;
import tech.steve.essaie.mapper.NaissanceMapper;
import tech.steve.essaie.model.*;
import tech.steve.essaie.repository.AnimalRepository;
import tech.steve.essaie.repository.CroisementRepository;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.repository.NaissanceRepository;
import tech.steve.essaie.service.AnimalService;
import tech.steve.essaie.service.HistoriqueModificationService;
import tech.steve.essaie.service.NaissanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.steve.essaie.service.QrCodeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NaissanceServiceImpl implements NaissanceService {

    private final NaissanceRepository naissanceRepository;
    private final NaissanceMapper naissanceMapper;
    private final AnimalRepository animalRepository;
    private final CroisementRepository croisementRepository;
    private final AnimalService animalService;
    private final HistoriqueModificationService historiqueService;
    private final FermeRepository fermeRepository;
    private final QrCodeService qrCodeService;
    private final AnimalMapper animalMapper;


    @Override
    public NaissanceDto create(NaissanceDto dto) {
        Ferme ferme = fermeRepository.findById(dto.fermeId()).orElseThrow(
                () -> new NotFoundException("ferme "+dto.fermeId())
        );

        Croisement croisement = croisementRepository.findById(dto.croisementId())
                .orElseThrow(() -> new NotFoundException("Croisement", dto.croisementId()));

        Naissance naissance = naissanceMapper.toEntity(dto);
        naissance.setDateNaissance(dto.dateNaissance() != null ? dto.dateNaissance() : LocalDate.now());
        naissance.setCroisement(croisement);
        naissance.setFerme(ferme);





//        // Génération des animaux enfants
//        for (int i = 0 ; i< dto.getNbMale();i++){
//            Animal animal = new Animal();
//
//            animal.setDateNaissance(LocalDate.now());
//            animal.setDateMort(LocalDate.now().plusMonths(24));
//            animal.setStatut(StatutAnimal.VIVANT);
//
//            Random random = new Random();
//            int randomInteger = random.nextInt(999999);
//            String code = String.format("%06d",randomInteger);
//
//            animal.setCode(code);
//            animal.setNom("porc" + code);
//
//            List<Integer> generationPere = animalRepository.findGenerationById(dto.getPereId());
//            List<Integer> generationMere = animalRepository.findGenerationById(dto.getMereId());
//
//            if (!generationPere.isEmpty() || !generationMere.isEmpty() ) { // Vérifie si la liste n'est pas vide
//                int result = Math.max(generationMere.get(0),generationPere.get(0));
//                animal.setGeneration(result + 1) ;
//            } else {
//                animal.setGeneration(1);
//            }
//
//            String qrCodeUrl = qrCodeService.genererQrCodePourAnimal(code);
//            animal.setQrCodeUrl(qrCodeUrl);
//
//            if (dto.getMereId() != null) {
//                animal.setMere(animalRepository.findById(dto.getMereId())
//                        .orElseThrow(() -> new RuntimeException("Mère introuvable")));
//            }
//
//            if (dto.getPereId() != null) {
//                animal.setPere(animalRepository.findById(dto.getPereId())
//                        .orElseThrow(() -> new RuntimeException("Père introuvable")));
//            }
//            animal.setFerme(ferme);
//            animal.setObservations(dto.getObservations());
//            animal.setSexe(Sexe.MALE);
//            Animal saved = animalRepository.save(animal);
//            animalMapper.toDto(saved);
//
//        }
//
//        for (int i = 0 ; i< dto.getNbFemmelle();i++){
//            Animal animal = new Animal();
//
//            animal.setDateNaissance(LocalDate.now());
//            animal.setDateMort(LocalDate.now().plusMonths(24));
//            animal.setStatut(StatutAnimal.VIVANT);
//
//            Random random = new Random();
//            int randomInteger = random.nextInt(999999);
//            String code = String.format("%06d",randomInteger);
//
//            animal.setCode(code);
//            animal.setNom("porc" + code);
//
//            List<Integer> generationPere = animalRepository.findGenerationById(dto.getPereId());
//            List<Integer> generationMere = animalRepository.findGenerationById(dto.getMereId());
//
//            if (!generationPere.isEmpty() || !generationMere.isEmpty() ) { // Vérifie si la liste n'est pas vide
//                int result = Math.max(generationMere.get(0),generationPere.get(0));
//                animal.setGeneration(result + 1) ;
//            } else {
//                animal.setGeneration(1);
//            }
//
//            String qrCodeUrl = qrCodeService.genererQrCodePourAnimal(code);
//            animal.setQrCodeUrl(qrCodeUrl);
//
//            if (dto.getMereId() != null) {
//                animal.setMere(animalRepository.findById(dto.getMereId())
//                        .orElseThrow(() -> new RuntimeException("Mère introuvable")));
//            }
//
//            if (dto.getPereId() != null) {
//                animal.setPere(animalRepository.findById(dto.getPereId())
//                        .orElseThrow(() -> new RuntimeException("Père introuvable")));
//            }
//            animal.setFerme(ferme);
//            animal.setObservations(dto.getObservations());
//            animal.setSexe(Sexe.FEMELLE);
//            Animal saved = animalRepository.save(animal);
//            animalMapper.toDto(saved);
//
//        }


        Naissance saved = naissanceRepository.save(naissance);

        historiqueService.enregistrerModification(
                "Naissance",
                saved.getId(),
                "Création",
                null,
                "Naissance enregistrée (Nb porcelets : " + dto.nbVivant() + ")",
                dto.nomIntervenant(),
                dto.telephoneIntervenant()

        );

        return naissanceMapper.toDto(saved);
    }

    @Override
    public NaissanceDto update(UUID id, NaissanceDto dto) {
        Naissance existing = naissanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Naissance", id));

        Ferme ferme = fermeRepository.findById(dto.fermeId()).orElseThrow(
                () -> new NotFoundException("ferme "+dto.fermeId())
        );

        Naissance updated = naissanceMapper.toEntity(dto);

        updated.setId(id);
        updated.setFerme(ferme);
        updated.setCroisement(existing.getCroisement());


        enregistrerChangement("Date naissance", existing.getDateNaissance(), updated.getDateNaissance(), dto);
        enregistrerChangement("Observation", existing.getObservations(), updated.getObservations(), dto);

        Naissance saved = naissanceRepository.save(updated);
        return naissanceMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {


        Naissance naissance = naissanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Naissance", id));

        // Archivage logique via historique
        historiqueService.enregistrerModification(
                "Naissance",
                id,
                "Archivage",
                null,
                "Naissance archivée",
                "INCONNU",
                "0000000000"
        );

        naissanceRepository.delete(naissance); // Peut être remplacé par un champ `archived = true` si on veut garder les données
    }



    @Override
    public NaissanceDto getById(UUID id) {
                return naissanceRepository.findById(id)
                .map(naissanceMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Naissance", id));
    }

    @Override
    public List<NaissanceDto> getAll() {
                return naissanceRepository.findAll().stream()
                .map(naissanceMapper::toDto)
                .toList();
    }

    @Override
    public List<NaissanceDto> getByMere(Animal mereId) {
                return null;
    }

    @Override
    public List<NaissanceDto> getByPere(Animal pereId) {
                return null;
    }

    @Override
    public List<NaissanceDto> getByCroisement(UUID croisementId) {
        return naissanceRepository.findById(croisementId).stream()
                .map(naissanceMapper::toDto)
                .toList();
    }

    @Override
    public List<HistoriqueModification> getHistoriqueModifications(UUID naissanceId) {
        return historiqueService.getHistoriqueParEntite("Naissance", naissanceId);
    }


    @Override
    public List<NaissanceDto> findByDate(LocalDate date) {
        return naissanceRepository.findByDateNaissance(date).stream()
                .map(naissanceMapper::toDto)
                .toList();
    }




    private void enregistrerChangement(String champ, Object ancienne, Object nouvelle, NaissanceDto dto) {
        Ferme ferme = fermeRepository.findById(dto.fermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.fermeId()));

        if (ancienne == null && nouvelle == null) return;
        if (ancienne != null && ancienne.equals(nouvelle)) return;

        historiqueService.enregistrerModification(
                "Naissance",
                dto.id(),
                champ,
                ancienne != null ? ancienne.toString() : null,
                nouvelle != null ? nouvelle.toString() : null,
                dto.nomIntervenant(),
                dto.telephoneIntervenant()
        );
    }


}