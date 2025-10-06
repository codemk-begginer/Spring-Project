package tech.steve.essaie.service.impl;

import tech.steve.essaie.dto.CroisementDto;
import tech.steve.essaie.dto.CroisementHistoriqueDto;
import tech.steve.essaie.enums.StatutGestation;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.CroisementMapper;
import tech.steve.essaie.model.Croisement;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.model.HistoriqueModification;
import tech.steve.essaie.repository.AnimalRepository;
import tech.steve.essaie.repository.CroisementRepository;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.service.CroisementService;
import tech.steve.essaie.service.HistoriqueModificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class CroisementServiceImpl implements CroisementService {

    private final CroisementRepository croisementRepository;
    private final CroisementMapper croisementMapper;
    private final HistoriqueModificationService historiqueService;
    private final AnimalRepository animalRepository;
    private final FermeRepository fermeRepository;



    @Override
    public CroisementDto create(CroisementDto dto) {
        Ferme ferme = fermeRepository.findById(dto.fermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.fermeId()));

        if (dto.truieId() != null && dto.verratId() != null && dto.truieId().equals(dto.verratId())) {
            throw new IllegalArgumentException("La mère et le père doivent être différents.");
        }

        Croisement croisement = croisementMapper.toEntity(dto);

        if (dto.dateCroisement() != null) {
            croisement.setDateMiseBasEstimee(dto.dateCroisement().plusDays(7));
        }

        croisement.setStatus(StatutGestation.EN_GESTATION);
        croisement.setDateCroisement(LocalDate.ofEpochDay(2025-03-30));
        croisement.setFerme(ferme);
        croisement = croisementRepository.save(croisement);

        historiqueService.enregistrerModification(
                "Croisement",
                croisement.getId(),
                "Création",
                null,
                "Croisement enregistré",
                croisement.getNomIntervenant(),
                croisement.getTelephoneIntervenant()

        );

        return croisementMapper.toDto(croisement);
    }

    @Override
    public CroisementDto update(UUID id, CroisementDto dto) {
        Ferme ferme = fermeRepository.findById(dto.fermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.fermeId()));

        Croisement existing = croisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Croisement", id));

        Croisement updated = croisementMapper.toEntity(dto);
        updated.setId(id);

        updated.setFerme(ferme);
        updated.setDateCroisement(LocalDate.now());

        enregistrerChangement("Statut", existing.getStatus(), updated.getStatus(), dto);
        enregistrerChangement("Nb porcelets", existing.getNbPorcelets(), updated.getNbPorcelets(), dto);
        enregistrerChangement("Date croisement", existing.getDateCroisement(), updated.getDateCroisement(), dto);

        if (updated.getDateCroisement() != null) {
            updated.setDateMiseBasEstimee(updated.getDateCroisement().plusDays(114));
        }

        Croisement saved = croisementRepository.save(updated);
        return croisementMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {

        Ferme ferme = new Ferme();
        ferme.setId(null);

                Croisement croisement = croisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Croisement", id));

        croisement.setStatus(StatutGestation.ARCHIVE);
        croisementRepository.save(croisement);

        historiqueService.enregistrerModification(
                "Croisement",
                croisement.getId(),
                "Archivage",
                null,
                "Croisement archivé",
                "INCONNU",
                "0000000000"


        );

    }

    @Override
    public CroisementDto findById(UUID id) {
        return croisementRepository.findById(id)
                .map(croisementMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Croisement", id));
    }

    @Override
    public List<CroisementDto> findAll() {
        return croisementRepository.findAll().stream()
                .filter(c -> c.getStatus() != StatutGestation.ARCHIVE)
                .map(croisementMapper::toDto)
                .toList();
    }

    @Override
    public List<CroisementDto> findByMere(UUID mereId) {
                return croisementRepository.findByTruieId(mereId).stream()
                .filter(c -> c.getStatus() != StatutGestation.ARCHIVE)
                .map(croisementMapper::toDto)
                .toList();
    }

    @Override
    public List<CroisementDto> findByPere(UUID pereId) {
                return croisementRepository.findByVerratId(pereId).stream()
                .filter(c -> c.getStatus() != StatutGestation.ARCHIVE)
                .map(croisementMapper::toDto)
                .toList();
    }

    @Override
    public List<CroisementDto> findByStatus(StatutGestation status) {
                return croisementRepository.findByStatus(status).stream()
                .filter(c -> c.getStatus() != StatutGestation.ARCHIVE)
                .map(croisementMapper::toDto)
                .toList();
    }

    @Override
    public List<CroisementHistoriqueDto> getHistoriqueCroisementsParAnimal(UUID animalId) {
                List<CroisementHistoriqueDto> enTantQueMere = croisementRepository.findByTruieId(animalId).stream()
                .map(c -> CroisementHistoriqueDto.builder()
                        .role("MERE")
                        .partenaireId(c.getVerrat() != null ? c.getVerrat().getId() : null)
                        .partenaireNom(c.getVerrat() != null ? c.getVerrat().getNom() : c.getNomVerratExterne())
                        .dateCroisement(c.getDateCroisement())
                        .dateMiseBasEstimee(c.getDateMiseBasEstimee())
                        .status(c.getStatus())
                        .nbPorcelets(c.getNbPorcelets())
                        .observations(c.getObservations())
                        .build())
                .toList();

        List<CroisementHistoriqueDto> enTantQuePere = croisementRepository.findByVerratId(animalId).stream()
                .map(c -> CroisementHistoriqueDto.builder()
                        .role("PERE")
                        .partenaireId(c.getTruie() != null ? c.getTruie().getId() : null)
                        .partenaireNom(c.getTruie() != null ? c.getTruie().getNom() : c.getNomTruieExterne())
                        .dateCroisement(c.getDateCroisement())
                        .dateMiseBasEstimee(c.getDateMiseBasEstimee())
                        .status(c.getStatus())
                        .nbPorcelets(c.getNbPorcelets())
                        .observations(c.getObservations())
                        .build())
                .toList();

        return Stream.concat(enTantQueMere.stream(), enTantQuePere.stream())
                .sorted(Comparator.comparing(CroisementHistoriqueDto::dateCroisement).reversed())
                .toList();
    }

    @Override
    public List<HistoriqueModification> getHistoriqueModifications(UUID croisementId) {
        return historiqueService.getHistoriqueParEntite("Croisement", croisementId);
    }






    private void enregistrerChangement(String champ, Object ancienne, Object nouvelle, CroisementDto dto) {
        Ferme ferme = fermeRepository.findById(dto.fermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.fermeId()));

        if (ancienne == null && nouvelle == null) return;
        if (ancienne != null && ancienne.equals(nouvelle)) return;

        historiqueService.enregistrerModification(
                "Croisement",
                dto.id(),
                champ,
                ancienne != null ? ancienne.toString() : null,
                nouvelle != null ? nouvelle.toString() : null,
                dto.nomIntervenant(),
                dto.telephoneIntervenant()

        );
    }
}