package tech.steve.essaie.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.steve.essaie.model.Validation;
import tech.steve.essaie.model.Validation;
import tech.steve.essaie.service.NotificationService;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    JavaMailSender javaMailSender;


    @Override
    public void envoyer(tech.steve.essaie.model.Validation validation) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@steve.tech");

        mailMessage.setTo(validation.getUtilisateur().getEmail());

        mailMessage.setSubject("Votre code d'activation");

        String texte =  String.format("Salut %s %s <br/> Votre code d' activation est %s; <br/> A Bientot ",
                validation.getUtilisateur().getPrenom(),
                validation.getUtilisateur().getNom(),
                validation.getCode());

        mailMessage.setText(texte);

        javaMailSender.send(mailMessage);
    }
}
