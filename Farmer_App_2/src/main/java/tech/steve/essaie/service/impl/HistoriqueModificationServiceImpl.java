package tech.steve.essaie.service.impl;

import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.model.HistoriqueModification;
import tech.steve.essaie.repository.HistoriqueModificationRepository;
import tech.steve.essaie.service.HistoriqueModificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueModificationServiceImpl implements HistoriqueModificationService {

    private final HistoriqueModificationRepository historiqueRepository;

    @Override
    public void enregistrerModification(String entite, Long idEntite, String champ, String ancienneValeur, String nouvelleValeur, String nomIntervenant, String telephoneIntervenant, Ferme ferme) {
        HistoriqueModification log = HistoriqueModification.builder()
                .entiteModifiee(entite)
                .idEntite(idEntite)
                .champModifie(champ)
                .ancienneValeur(ancienneValeur)
                .nouvelleValeur(nouvelleValeur)
                .dateModification(LocalDateTime.now())
                .nomIntervenant(nomIntervenant)
                .telephoneIntervenant(telephoneIntervenant)
                .ferme(ferme)
                .build();

        historiqueRepository.save(log);
    }

    @Override
    public List<HistoriqueModification> getHistoriqueParEntite(String entite, Long idEntite) {
        return historiqueRepository.findByEntiteModifieeAndIdEntiteOrderByDateModificationDesc(entite, idEntite);
    }

    @Override
    public void enregistrerModification(String animal, Long id, Animal beforeUpdate, Animal updated) {

    }
}
