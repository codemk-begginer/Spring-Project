package tech.steve.essaie.service;

import org.springframework.security.core.userdetails.UserDetails;
import tech.steve.essaie.dto.UtilisateurDto;
import tech.steve.essaie.model.Role;

import java.util.List;
import java.util.Map;

public interface UtilisateurService  {
    UtilisateurDto create(UtilisateurDto dto);
    UtilisateurDto update(Long id, UtilisateurDto dto);
    UtilisateurDto findById(Long id);
    List<UtilisateurDto> findAll();
    public void modifierMotDePasse(Map<String, String> parametres);
    public void nouveauMotDePasse(Map<String, String> parametres);
    public void activation(Map<String, String> activation);
    public UserDetails loadUserByUsername(String username);
    public void delete(Long id);
    public List<UtilisateurDto> findByActif(boolean actif);
    public List<UtilisateurDto> findByFerme(Long fermeId);
    public UtilisateurDto findByEmail(String email);

    public List<UtilisateurDto> findByRole(Role role);
    void assignRole(Long utilisateurId, String role);
    void archive(Long id);
    UtilisateurDto login(String email, String motDePasse);
}
