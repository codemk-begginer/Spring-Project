package com.porc.service.impl;

package com.porc.service.impl;

import com.porc.dto.UtilisateurDto;

import com.porc.exception.NotFoundException;
import com.porc.mapper.UtilisateurMapper;
import com.porc.model.Ferme;
import com.porc.model.Utilisateur;
import com.porc.repository.FermeRepository;
import com.porc.repository.UtilisateurRepository;
import com.porc.service.UtilisateurService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final FermeRepository fermeRepository;
    private final UtilisateurMapper utilisateurMapper;

    @Override
    @Transactional
    public UtilisateurDto create(UtilisateurDto dto) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(dto);

        // Affectation automatique du statut et horodatage
        utilisateur.setStatut(StatutUtilisateur.ACTIF);
        utilisateur.setHorodatage(LocalDateTime.now());

        // Gestion de la ferme
        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec id : " + dto.getFermeId()));
        utilisateur.setFerme(ferme);

        // Rôle
        Role role = roleRepository.findByNom(dto.getRole())
                .orElseThrow(() -> new NotFoundException("Rôle non trouvé : " + dto.getRole()));
        utilisateur.setRole(role);

        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    @Transactional
    public UtilisateurDto update(Long id, UtilisateurDto dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec id : " + id));

        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setTelephone(dto.getTelephone());

        if (dto.getRole() != null) {
            Role role = roleRepository.findByNom(dto.getRole())
                    .orElseThrow(() -> new NotFoundException("Rôle non trouvé : " + dto.getRole()));
            utilisateur.setRole(role);
        }

        utilisateur.setStatut(dto.getStatut());
        utilisateur.setHorodatage(LocalDateTime.now());

        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec id : " + id));
        utilisateur.setStatut(StatutUtilisateur.ARCHIVE);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public UtilisateurDto findById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec id : " + id));
        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UtilisateurDto> findByFerme(Long fermeId) {
        return utilisateurRepository.findByFermeId(fermeId).stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UtilisateurDto> findByStatut(String statut) {
        return utilisateurRepository.findByStatut(StatutUtilisateur.valueOf(statut.toUpperCase())).stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec email : " + email));
        return utilisateurMapper.toDto(utilisateur);
    }
}