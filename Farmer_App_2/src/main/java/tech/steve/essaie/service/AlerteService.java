package tech.steve.essaie.service;

import org.springframework.scheduling.annotation.Scheduled;
import tech.steve.essaie.dto.AlerteDto;

import java.util.List;

public interface AlerteService {


    void archive(Long id);
    AlerteDto findById(Long id);
    List<AlerteDto> findAll();
    List<AlerteDto> findByType(String type);
    List<AlerteDto> findByStatut(String statut);


    //@Scheduled(cron = "@daily")
    //@Scheduled(fixedRate = 60000) // S'exécute toutes les 60 secondes (60000 ms)

    void genererAlertesAutomatiques();

    List<AlerteDto> findNonLues();

    List<AlerteDto> findByAnimal(Long animalId);

    void marquerCommeLue(Long id);


    void supprimer(Long id);


}