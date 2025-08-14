package com.porc.service;

import com.porc.model.HistoriqueModification;

import java.util.List;

public interface HistoriqueModificationService {
    void enregistrerModification(String entite, Long idEntite, String champ, String ancienneValeur, String nouvelleValeur, String nomIntervenant, String telephoneIntervenant);
    List<HistoriqueModification> getHistoriqueParEntite(String entite, Long idEntite);
}
