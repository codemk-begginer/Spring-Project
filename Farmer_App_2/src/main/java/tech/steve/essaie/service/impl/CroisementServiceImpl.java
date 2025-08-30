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
        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.getFermeId()));

        if (dto.getMereId() != null && dto.getPereId() != null && dto.getMereId().equals(dto.getPereId())) {
            throw new IllegalArgumentException("La mère et le père doivent être différents.");
        }

        Croisement croisement = croisementMapper.toEntity(dto);

        if (dto.getDateCroisement() != null) {
            croisement.setDateMiseBasEstimee(dto.getDateCroisement().plusDays(7));
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
                croisement.getTelephoneIntervenant(),
                croisement.getFerme()
        );

        return croisementMapper.toDto(croisement);
    }

    @Override
    public CroisementDto update(Long id, CroisementDto dto) {
        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.getFermeId()));

        Croisement existing = croisementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Croisement", id));

        Croisement updated = croisementMapper.toEntity(dto);
        updated.setId(id);

        updated.setFerme(ferme);

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
    public void delete(Long id) {

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
                "0000000000",
                ferme

        );

    }

    @Override
    public CroisementDto findById(Long id) {
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
    public List<CroisementDto> findByMere(Long mereId) {
                return croisementRepository.findByMereId(mereId).stream()
                .filter(c -> c.getStatus() != StatutGestation.ARCHIVE)
                .map(croisementMapper::toDto)
                .toList();
    }

    @Override
    public List<CroisementDto> findByPere(Long pereId) {
                return croisementRepository.findByPereId(pereId).stream()
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
    public List<CroisementHistoriqueDto> getHistoriqueCroisementsParAnimal(Long animalId) {
                List<CroisementHistoriqueDto> enTantQueMere = croisementRepository.findByMereId(animalId).stream()
                .map(c -> CroisementHistoriqueDto.builder()
                        .role("MERE")
                        .partenaireId(c.getPere() != null ? c.getPere().getId() : null)
                        .partenaireNom(c.getPere() != null ? c.getPere().getNom() : c.getNomPereExterne())
                        .dateCroisement(c.getDateCroisement())
                        .dateMiseBasEstimee(c.getDateMiseBasEstimee())
                        .status(c.getStatus())
                        .nbPorcelets(c.getNbPorcelets())
                        .observations(c.getObservations())
                        .build())
                .toList();

        List<CroisementHistoriqueDto> enTantQuePere = croisementRepository.findByPereId(animalId).stream()
                .map(c -> CroisementHistoriqueDto.builder()
                        .role("PERE")
                        .partenaireId(c.getMere() != null ? c.getMere().getId() : null)
                        .partenaireNom(c.getMere() != null ? c.getMere().getNom() : c.getNomMereExterne())
                        .dateCroisement(c.getDateCroisement())
                        .dateMiseBasEstimee(c.getDateMiseBasEstimee())
                        .status(c.getStatus())
                        .nbPorcelets(c.getNbPorcelets())
                        .observations(c.getObservations())
                        .build())
                .toList();

        return Stream.concat(enTantQueMere.stream(), enTantQuePere.stream())
                .sorted(Comparator.comparing(CroisementHistoriqueDto::getDateCroisement).reversed())
                .toList();
    }

    @Override
    public List<HistoriqueModification> getHistoriqueModifications(Long croisementId) {
        return historiqueService.getHistoriqueParEntite("Croisement", croisementId);
    }






    private void enregistrerChangement(String champ, Object ancienne, Object nouvelle, CroisementDto dto) {
        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec l'ID : " + dto.getFermeId()));

        if (ancienne == null && nouvelle == null) return;
        if (ancienne != null && ancienne.equals(nouvelle)) return;

        historiqueService.enregistrerModification(
                "Croisement",
                dto.getId(),
                champ,
                ancienne != null ? ancienne.toString() : null,
                nouvelle != null ? nouvelle.toString() : null,
                dto.getNomIntervenant(),
                dto.getTelephoneIntervenant(),
                ferme
        );
    }
}