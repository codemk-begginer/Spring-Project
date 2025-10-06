package tech.steve.essaie.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.steve.essaie.dto.OperationDto;
import tech.steve.essaie.enums.TypeOperation;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.OperationMapper;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.model.HistoriqueModification;
import tech.steve.essaie.model.Operation;
import tech.steve.essaie.repository.AnimalRepository;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.repository.OperationRepository;
import tech.steve.essaie.service.HistoriqueModificationService;
import tech.steve.essaie.service.OperationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Transactional
public class OperationServiceImpl implements OperationService {

    private final FermeRepository fermeRepository;
    private final AnimalRepository animalRepository;
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private final HistoriqueModificationService historiqueService;

    @Override
    public OperationDto create(OperationDto dto) {
        Ferme ferme = fermeRepository.findById(dto.fermeId()).orElseThrow(
                () -> new NotFoundException("ferme "+dto.fermeId())
        );
        Animal animal = animalRepository.findById(dto.animalId()).orElseThrow(
                () -> new NotFoundException("animal "+dto.animalId())
        );

        Operation operation = operationMapper.toEntity(dto);
        operation.setAnimal(animal);
        operation.setFerme(ferme);

        Operation saved = operationRepository.save(operation);

        // Historique : enregistrer les champs initiaux
        enregistrerSiNonNul("type", nullSafeToString(saved.getType()), saved);
        enregistrerSiNonNul("produit", saved.getProduit(), saved);
        enregistrerSiNonNul("dose", nullSafeToString(saved.getDose()), saved);
        enregistrerSiNonNul("cout", nullSafeToString(saved.getCout()), saved);
        enregistrerSiNonNul("observations", saved.getObservations(), saved);

        return operationMapper.toDto(saved);
    }



    @Override
    public OperationDto update(UUID id, OperationDto dto) {
        Operation existing = operationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Opération introuvable avec l'ID " + id));

        Ferme ferme = fermeRepository.findById(dto.fermeId())
                .orElseThrow(() -> new  NotFoundException("ferme non trouver avec l'id  " + id ));

        // Copie pour comparaison
        Operation before = Operation.builder()
                .id(existing.getId())
                .type(existing.getType())
                .produit(existing.getProduit())
                .dose(existing.getDose())
                .cout(existing.getCout())
                .nomIntervenant(existing.getNomIntervenant())
                .telephoneIntervenant(existing.getTelephoneIntervenant())
                .observations(existing.getObservations())
                .build();

        // Mise à jour des champs
        existing.setFerme(ferme);
        existing.setType(dto.type());
        existing.setProduit(dto.produit());
        existing.setDose(dto.dose());
        existing.setCout(dto.cout());
        existing.setNomIntervenant(dto.nomIntervenant());
        existing.setTelephoneIntervenant(dto.telephoneIntervenant());
        existing.setObservations(dto.observations());

        Operation updated = operationRepository.save(existing);

        // Enregistrement de l’historique champ par champ
        if (!Objects.equals(before.getType(), updated.getType()))
            enregistrerHist("type", String.valueOf(before.getType()), String.valueOf(updated.getType()), updated);
        if (!Objects.equals(before.getProduit(), updated.getProduit()))
            enregistrerHist("produit", before.getProduit(), updated.getProduit(), updated);
        if (!Objects.equals(before.getDose(), updated.getDose()))
            enregistrerHist("dose", nullSafeToString(before.getDose()), nullSafeToString(updated.getDose()), updated);
        if (!Objects.equals(before.getCout(), updated.getCout()))
            enregistrerHist("cout", String.valueOf(before.getCout()), String.valueOf(updated.getCout()), updated);
        if (!Objects.equals(before.getObservations(), updated.getObservations()))
            enregistrerHist("observations", before.getObservations(), updated.getObservations(), updated);

        return operationMapper.toDto(updated);
    }


    @Override
    public OperationDto findById(UUID id) {
                Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Opération introuvable avec l'ID " + id));
        return operationMapper.toDto(operation);
    }



    @Override
    public List<OperationDto> findByAnimal(UUID animalId) {
                return operationRepository.findByAnimalId(animalId).stream()
                .map(operationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationDto> findByType(TypeOperation type) {
                return operationRepository.findByType(type).stream()
                .map(operationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationDto> findByDateRange(LocalDate startDate, LocalDate endDate) {
                return operationRepository.findByDateBetween(startDate, endDate).stream()
                .map(operationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoriqueModification> getHistoriqueModifications(UUID operationId) {
        return historiqueService.getHistoriqueParEntite("Operation", operationId);
    }

    @Override
    public void delete(UUID id) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Opération introuvable avec l'ID " + id));

        operationRepository.deleteById(id);

        historiqueService.enregistrerModification(
                "Operation",
                operation.getId(),
                "SUPPRESSION",
                null,
                null,
                operation.getNomIntervenant() != null ? operation.getNomIntervenant() : "INCONNU",
                operation.getTelephoneIntervenant() != null ? operation.getTelephoneIntervenant() : "0000000000"
        );
    }

    @Override
    public List<OperationDto> getAll() {
        return operationRepository.findAll().stream()
                .map(operationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OperationDto> searchByKeyword(String keyword) {
        return operationRepository.findByProduitContainingIgnoreCaseOrObservationsContainingIgnoreCase(keyword, keyword)
                .stream().map(operationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void archive(UUID id) {

    }


    @Override
    public double getTotalCoutByType(String type) {
        return 0;
    }





    private void enregistrerHist(String champ, String ancienne, String nouvelle, Operation operation) {
        historiqueService.enregistrerModification(
                "Operation",
                operation.getId(),
                champ,
                ancienne,
                nouvelle,
                operation.getNomIntervenant() != null ? operation.getNomIntervenant() : "INCONNU",
                operation.getTelephoneIntervenant() != null ? operation.getTelephoneIntervenant() : "0000000000"
        );
    }


    private void enregistrerSiNonNul(String champ, String valeur, Operation operation) {
        if (valeur != null && !valeur.isBlank()) {
            historiqueService.enregistrerModification(
                    "Operation",
                    operation.getId(),
                    champ,
                    null, // aucune ancienne valeur lors de la création
                    valeur,
                    operation.getNomIntervenant() != null ? operation.getNomIntervenant() : "INCONNU",
                    operation.getTelephoneIntervenant() != null ? operation.getTelephoneIntervenant() : "0000000000"
            );
        }
    }



    private String nullSafeToString(Object obj) {
        return (obj == null) ? null : obj.toString();
    }
}
