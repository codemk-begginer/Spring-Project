package tech.chilo.avis.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.chilo.avis.entite.Validation;

@AllArgsConstructor
@Service
public class NotificationService {
    JavaMailSender javaMailSender;

    public void envoyer(Validation validation){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@chillo.tech");

        mailMessage.setTo(validation.getUtilisateur().getEmail());

        mailMessage.setSubject("Votre code d'activation");

      String texte =  String.format("Salut %s <br/> Votre code d' activation est %s; A Bientot ",
                validation.getUtilisateur().getNom(),
                validation.getCode());

      mailMessage.setText(texte);

      javaMailSender.send(mailMessage);

    }
}
