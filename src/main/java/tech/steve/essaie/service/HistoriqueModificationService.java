package tech.steve.essaie.service;

import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.model.HistoriqueModification;

import java.util.List;
import java.util.UUID;

public interface HistoriqueModificationService {
    void enregistrerModification(String entite, UUID idEntite, String champ, String ancienneValeur, String nouvelleValeur, String nomIntervenant, String telephoneIntervenant);
    List<HistoriqueModification> getHistoriqueParEntite(String entite, UUID idEntite);


    void enregistrerModification(String animal, UUID id, Animal beforeUpdate, Animal updated);
}
