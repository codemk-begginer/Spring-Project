package tech.steve.essaie.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.steve.essaie.dto.UtilisateurDto;
import tech.steve.essaie.exceptions.NotFoundException;
import tech.steve.essaie.mapper.UtilisateurMapper;
import tech.steve.essaie.model.Ferme;
import tech.steve.essaie.model.Role;
import tech.steve.essaie.model.Utilisateur;
import tech.steve.essaie.model.Validation;
import tech.steve.essaie.repository.FermeRepository;
import tech.steve.essaie.repository.RoleRepository;
import tech.steve.essaie.repository.UtilisateurRepository;
import tech.steve.essaie.service.UtilisateurService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService , UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final FermeRepository fermeRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationServiceImpl validationService;

    @Override
    @Transactional
    public UtilisateurDto create(UtilisateurDto dto) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(dto);

        // Affectation automatique du statut et horodatage
//        utilisateur.setActif(true);
        utilisateur.setHorodatage(LocalDateTime.now());

        // Gestion de la ferme
        Ferme ferme = fermeRepository.findById(dto.getFermeId())
                .orElseThrow(() -> new NotFoundException("Ferme non trouvée avec id : " + dto.getFermeId()));
        utilisateur.setFerme(ferme);

        // Rôle
        Role role = roleRepository.findByLibelle(dto.getRole())
                .orElseThrow(() -> new NotFoundException("Rôle non trouvé : " + dto.getRole()));
        utilisateur.setRole(role.getLibelle());

        if(!utilisateur.getEmail().contains("@") || !utilisateur.getEmail().contains(".")){
            throw new RuntimeException("Votre email est invalide");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(dto.getEmail());

        if(utilisateurOptional.isPresent()){
            throw new RuntimeException("Votre email est déjas utiliser ");
        }

        String mdpCrypte = this.passwordEncoder.encode(dto.getMotDePasse());
        utilisateur.setMotDePasse(mdpCrypte);

        utilisateur = utilisateurRepository.save(utilisateur);
        this.validationService.enregistrer(utilisateur);
        return utilisateurMapper.toDto(utilisateur);
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));

        if(Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Votre code a expirer , <br/> demandez un nouveau !!!");
        }

        Utilisateur utilisateurActiver =  this.utilisateurRepository.findById(validation.getUtilisateur()
                .getId()).orElseThrow(()-> new RuntimeException("Utilisateur inconue"));
        utilisateurActiver.setActif(true);

        this.utilisateurRepository.save(utilisateurActiver);

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
            Role role = roleRepository.findByLibelle(dto.getRole())
                    .orElseThrow(() -> new NotFoundException("Rôle non trouvé : " + dto.getRole()));
            utilisateur.setRole(role.getLibelle());
        }


        utilisateur.setHorodatage(LocalDateTime.now());

        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public void archive(Long id) {

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
    public UtilisateurDto login(String email, String motDePasse) {
        return null;
    }



    @Override
    public void assignRole(Long utilisateurId, String role) {

    }

    @Override
    public void modifierMotDePasse(Map<String, String> parametres) {
        Utilisateur utilisateur = (Utilisateur) this.loadUserByUsername(parametres.get("email"));
        this.validationService.enregistrer(utilisateur);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository.findByEmail(username)
                .orElseThrow(
                        () -> new
                                UsernameNotFoundException("aucun utilisateur ne correspond a cette identifiant ") );
    }

    @Override
    public void nouveauMotDePasse(Map<String, String> parametres) {

        Utilisateur utilisateur = (Utilisateur) this.loadUserByUsername(parametres.get("email"));
        final Validation validation = validationService.lireEnFonctionDuCode(parametres.get("code"));
        if(validation.getUtilisateur().getEmail().equals(utilisateur.getEmail())) {
            String mdpCrypte = this.passwordEncoder.encode(parametres.get("password"));
            utilisateur.setMotDePasse(mdpCrypte);
            this.utilisateurRepository.save(utilisateur);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec id : " + id));
        utilisateur.setActif(false);
        utilisateurRepository.save(utilisateur);
    }

    public List<UtilisateurDto> findByActif(boolean actif) {
                return utilisateurRepository.findByActif(actif).stream()
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
    public UtilisateurDto findByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec email : " + email));
        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public List<UtilisateurDto> findByRole(Role role) {
        return List.of();
    }

}