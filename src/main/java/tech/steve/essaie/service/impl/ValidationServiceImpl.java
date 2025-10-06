package tech.steve.essaie.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tech.steve.essaie.model.Utilisateur;
import tech.steve.essaie.model.Validation;
import tech.steve.essaie.repository.ValidationsRepository;
import tech.steve.essaie.model.Utilisateur;
import tech.steve.essaie.service.NotificationService;
import tech.steve.essaie.service.ValidationService;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class ValidationServiceImpl implements ValidationService {

   private ValidationsRepository validationsRepository;



   private NotificationService notificationService;


    @Override
    public void enregistrer(tech.steve.essaie.model.Utilisateur utilisateur) {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expiration = Instant.now();
        expiration = expiration.plus(10, MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d",randomInteger);

        validation.setCode(code);

        this.validationsRepository.save(validation);

        this.notificationService.envoyer(validation);
    }

    public Validation lireEnFonctionDuCode(String code){
       return this.validationsRepository.findByCode(code).orElseThrow(() -> new RuntimeException("votre code est invalide")) ;
    }


//    @Scheduled(cron = "@daily")
//     @Scheduled(cron = "0 */10 * * * *")
    public void nettoyerTable() {
        final Instant now = Instant.now();
        log.info("Suppression des codes expirés à {}", now);
        this.validationsRepository.deleteAllByExpirationBefore(Instant.now());
    }



}
