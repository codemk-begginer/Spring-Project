package tech.steve.essaie.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import tech.steve.essaie.dto.AlerteDto;
import tech.steve.essaie.enums.StatutGestation;
import tech.steve.essaie.enums.TypeOperation;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.AlerteMapper;
import tech.steve.essaie.model.*;
import tech.steve.essaie.repository.*;
import tech.steve.essaie.service.AlerteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AlerteServiceImpl implements AlerteService {

    private final AlerteRepository alerteRepository;
    private final AlerteMapper alerteMapper;
    private final CroisementRepository croisementRepository;
    private final AnimalRepository animalRepository;
    private final OperationRepository operationRepository;
    private  final FermeRepository fermeRepository;

    // Génère un UUID aléatoire
    UUID uuid = UUID.randomUUID();

    //@Scheduled(fixedRate = 60000) // cette tache s'execute tous les 60 sec
    //@Scheduled(cron = "@daily")
    @Scheduled(cron = "0 */1 * * * *") //la tache est executertoutes les minutes
    @Override
    public void genererAlertesAutomatiques() {

        // 1. Gestation proche du terme (dans 7 jours)

       LocalDate today = LocalDate.now();
        List<Croisement> croisements = croisementRepository.findAll();
        for (Croisement c : croisements) {
            if (c.getDateMiseBasEstimee() != null &&
                    c.getDateMiseBasEstimee().minusDays(7).equals(today) &&
                    c.getStatus() == StatutGestation.EN_GESTATION) {
                saveAlerte("GESTATION_TERMINEE",
                        "La mise bas estimée approche pour " + c.getTruie().getNom(),
                        c.getTruie());
            }

        }


        // 2. Absence de croisement depuis > 150 jours
//        List<Animal> femelles = animalRepository.findBySexe(Sexe.FEMELLE);
//        log.info("Alerte Genere avec succes a {}", Instant.now());
//        log.info(femelles.toString());
//        for (Animal f : femelles) {
//            List<Croisement> dernierCroisement = croisementRepository.findDateCroisementByMereCode(f.getMere().getCode());
//            log.info(dernierCroisement.toString());
//            if (dernierCroisement != null &&
//                    today.minus(150).equals(dernierCroisement)
//                    ChronoUnit.DAYS.between( dernierCroisement, today) > 150) {
//                saveAlerte("CROISEMENT_REQUIS",
//                        "Aucun croisement détecté pour " + f.getNom() + " depuis plus de 150 jours.",
//                        f);
//            }
//        }


        // 3. Dernière vaccination > 180 jours
//        List<Animal> tous = animalRepository.findAll();
//        log.info("Alerte generer avec success !!");
//        for (Animal a : tous) {
//            List<Operation> derniereVaccination = operationRepository.findLastDateByAnimalIdAndType(a.getId(), TypeOperation.VACCINATION);
//
//            if (derniereVaccination != null &&
//                   // ChronoUnit.DAYS.between(derniereVaccination.get(0), today) > 180)
//            today.minusDays(180).isBefore((ChronoLocalDate) derniereVaccination))
//                {
//                saveAlerte("VACCINATION_EN_RETARD",
//                        "La dernière vaccination de " + a.getNom() + " remonte à plus de 180 jours.",
//                        a);
//            }
//        }




        // 4. Inactivité > 365 jours
//        for (Animal a : tous) {
//            if (a.getStatut() == StatutAnimal.ARCHIVE) continue;
//            LocalDate derniereOp = operationRepository.findLastDateByAnimal(a.getId());
//            if (derniereOp != null &&
//                    ChronoUnit.DAYS.between(derniereOp, today) > 365) {
//                saveAlerte("INACTIVITE_PROUUIDEE",
//                        "L’animal " + a.getNom() + " n’a reçu aucune opération depuis plus d’un an.",
//                        a);
//            }
//        }
    }

    private void saveAlerte(String type, String message, Animal animal) {


        Alerte alerte = Alerte.builder()
                .type(type)
                .message(message)
                .date(LocalDate.now())
                .lue(false)
                .animal(animal)
                .status("ACTIF")
                .build();
        alerteRepository.save(alerte);
    }

    @Override
    public List<AlerteDto> findNonLues() {
        return alerteRepository.findByLueFalse().stream()
                .map(alerteMapper::toDto)
                .toList();

    }

//    @Override
//    public List<AlerteDto> findByAnimal(UUID animalId) {
//        return List.of();
//    }

    @Override
    public List<AlerteDto> findByAnimal(UUID animalId) {
        return alerteRepository.findByAnimalId(animalId).stream()
                .map(alerteMapper::toDto)
                .toList();
    }

    @Override
    public void marquerCommeLue(UUID id) {
        Alerte alerte = alerteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alerte", id));
        alerte.setLue(true);
    }

    @Override
    public void supprimer(UUID id) {
        alerteRepository.deleteById(id); // suppression autorisée ici
    }

    @Override
    public List<AlerteDto> findAll() {
        return alerteRepository.findAll().stream()
                .map(alerteMapper::toDto)
                .toList();
    }

    @Override
    public List<AlerteDto> findByType(String type) {
        return alerteRepository.findByType(type).stream()
                .map(alerteMapper::toDto)
                .toList();
    }

    @Override
    public AlerteDto findById(UUID id) {
        Alerte alerte = alerteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec id : " + id));
        return alerteMapper.toDto(alerte);
    }


    @Override
    public List<AlerteDto> findByStatut(String statut) {
        return alerteRepository.findByStatus(statut).stream()
                .map(alerteMapper::toDto)
                .toList();
    }



    @Override
    public void archive(UUID id) {
        Alerte alerte = alerteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alerte", id));
        alerte.setStatus("ARCHIVE");
    }




}