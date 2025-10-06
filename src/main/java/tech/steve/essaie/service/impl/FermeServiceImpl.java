package tech.steve.essaie.service.impl;

import tech.steve.essaie.dto.ferme.FermeDto;
import tech.steve.essaie.dto.ferme.FermeUpdateDto;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.FermeMapper;
import tech.steve.essaie.mapper.FermeUpdateMapper;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.service.FermeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FermeServiceImpl implements FermeService {

    private final FermeRepository fermeRepository;
    private final FermeMapper fermeMapper;
    private final FermeUpdateMapper fermeUpdateMapper;



    @Override
    public FermeDto create(FermeDto dto) {
        Ferme ferme = fermeMapper.toEntity(dto);
        ferme.setDateCreation(Instant.now());
        ferme.setDateModification(LocalDateTime.now());
        return fermeMapper.toDto(fermeRepository.save(ferme));
    }

    @Override
    public FermeUpdateDto update(UUID id, FermeUpdateDto dto) {
        Ferme ferme = fermeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ferme", id));
        ferme.setNom(dto.nom());
        ferme.setAdresse(dto.adresse());
        ferme.setTelephone(dto.telephone());
        ferme.setEmail(dto.email());
        ferme.setDateModification(LocalDateTime.now());
        return fermeUpdateMapper.toDto(fermeRepository.save(ferme));
    }

    @Override
    public void delete(UUID id) {
        Ferme ferme = fermeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ferme", id));
        fermeRepository.delete(ferme);
    }

    @Override
    public FermeDto findById(UUID id) {
        return fermeMapper.toDto(
                fermeRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Ferme", id))
        );
    }

    @Override
    public List<FermeDto> findAll() {
        return fermeRepository.findAll().stream()
                .map(fermeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FermeDto> findByNom(String nom) {
        return List.of();
    }

    @Override
    public void assignerUtilisateur(UUID fermeId, UUID utilisateurId) {

    }
}
