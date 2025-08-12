package tech.steve.farmer_app.service;

import tech.steve.farmer_app.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDto create(UtilisateurDto dto);
    UtilisateurDto update(Long id, UtilisateurDto dto);
    void archive(Long id);
    UtilisateurDto findById(Long id);
    List<UtilisateurDto> findAll();
    UtilisateurDto login(String email, String motDePasse);
    List<UtilisateurDto> findByRole(String role);
    void assignRole(Long utilisateurId, String role);
    void changerMotDePasse(Long id, String nouveauMdp);
}
