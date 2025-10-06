package tech.steve.essaie.service;

import org.springframework.security.core.userdetails.UserDetails;
import tech.steve.essaie.dto.utilisateur.UtilisateurDto;
import tech.steve.essaie.dto.utilisateur.UtilisateurUpdateDto;
import tech.steve.essaie.model.Role;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UtilisateurService  {
    UtilisateurDto create(UtilisateurDto dto);
    UtilisateurUpdateDto update(UUID id, UtilisateurUpdateDto dto);
    UtilisateurUpdateDto findById(UUID id);
    List<UtilisateurUpdateDto> findAll();
    public void modifierMotDePasse(Map<String, String> parametres);
    public void nouveauMotDePasse(Map<String, String> parametres);
    public void activation(Map<String, String> activation);
    public UserDetails loadUserByUsername(String username);
    public void delete(UUID id);
    public List<UtilisateurUpdateDto> findByActif(boolean actif);
    public List<UtilisateurUpdateDto> findByFerme(UUID fermeId);
    public UtilisateurUpdateDto findByEmail(String email);

    public List<UtilisateurDto> findByRole(Role role);
    void assignRole(UUID utilisateurId, String role);
    void archive(UUID id);
    UtilisateurDto login(String email, String motDePasse);
}
