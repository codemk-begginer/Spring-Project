package tech.steve.essaie.mapper;

import tech.steve.essaie.dto.UtilisateurDto;
import tech.steve.essaie.model.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    UtilisateurDto toDto(Utilisateur utilisateur);
    Utilisateur toEntity(UtilisateurDto dto);
}