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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoriqueModificationServiceImpl implements HistoriqueModificationService {

    private final HistoriqueModificationRepository historiqueRepository;

    @Override
    public void enregistrerModification(String entite, UUID idEntite, String champ, String ancienneValeur, String nouvelleValeur, String nomIntervenant, String telephoneIntervenant) {
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
    public List<HistoriqueModification> getHistoriqueParEntite(String entite, UUID idEntite) {
        return historiqueRepository.findByEntiteModifieeAndIdEntiteOrderByDateModificationDesc(entite, idEntite);
    }

    @Override
    public void enregistrerModification(String animal, UUID id, Animal beforeUpdate, Animal updated) {

    }
}
