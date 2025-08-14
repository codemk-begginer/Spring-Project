package com.porc.service.impl;

import com.porc.model.HistoriqueModification;
import com.porc.repository.HistoriqueModificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.porc.service.HistoriqueModificationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueModificationServiceImpl implements HistoriqueModificationService {

    private final HistoriqueModificationRepository historiqueRepository;

    @Override
    public void enregistrerModification(String entite, Long idEntite, String champ, String ancienneValeur, String nouvelleValeur, String nomIntervenant, String telephoneIntervenant) {
        HistoriqueModification log = HistoriqueModification.builder()
                .entiteModifiee(entite)
                .idEntite(idEntite)
                .champModifie(champ)
                .ancienneValeur(ancienneValeur)
                .nouvelleValeur(nouvelleValeur)
                .dateModification(LocalDateTime.now())
                .nomIntervenant(nomIntervenant)
                .telephoneIntervenant(telephoneIntervenant)
                .build();

        historiqueRepository.save(log);
    }

    @Override
    public List<HistoriqueModification> getHistoriqueParEntite(String entite, Long idEntite) {
        return historiqueRepository.findByEntiteModifieeAndIdEntiteOrderByDateModificationDesc(entite, idEntite);
    }
}
