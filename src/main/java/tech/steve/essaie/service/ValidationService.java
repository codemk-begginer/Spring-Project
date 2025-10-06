package tech.steve.essaie.service;

import tech.steve.essaie.model.Utilisateur;
import tech.steve.essaie.model.Validation;

public interface ValidationService {
    
    public void enregistrer(Utilisateur utilisateur);
    
    public Validation lireEnFonctionDuCode(String code);
}
