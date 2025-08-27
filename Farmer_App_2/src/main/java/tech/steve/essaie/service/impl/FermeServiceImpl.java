package tech.steve.essaie.service.impl;

import tech.steve.essaie.dto.FermeDto;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.FermeMapper;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.service.FermeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FermeServiceImpl implements FermeService {

    private final FermeRepository fermeRepository;
    private final FermeMapper fermeMapper;

    @Override
    public FermeDto create(FermeDto dto) {
        Ferme ferme = fermeMapper.toEntity(dto);
        return fermeMapper.toDto(fermeRepository.save(ferme));
    }

    @Override
    public FermeDto update(Long id, FermeDto dto) {
        Ferme ferme = fermeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ferme", id));
        ferme.setNom(dto.getNom());
        ferme.setAdresse(dto.getAdresse());
        ferme.setTelephone(dto.getTelephone());
        ferme.setEmail(dto.getEmail());
        return fermeMapper.toDto(fermeRepository.save(ferme));
    }

    @Override
    public void delete(Long id) {
        Ferme ferme = fermeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ferme", id));
        fermeRepository.delete(ferme);
    }

    @Override
    public FermeDto findById(Long id) {
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
    public void assignerUtilisateur(Long fermeId, Long utilisateurId) {

    }
}
