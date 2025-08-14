package tech.steve.essaie.service;

import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.HistoriqueModification;

import java.util.List;

public interface HistoriqueModificationService {
    void enregistrerModification(String entite, Long idEntite, String champ, String ancienneValeur, String nouvelleValeur, String nomIntervenant, String telephoneIntervenant);
    List<HistoriqueModification> getHistoriqueParEntite(String entite, Long idEntite);


    void enregistrerModification(String animal, Long id, Animal beforeUpdate, Animal updated);
}
