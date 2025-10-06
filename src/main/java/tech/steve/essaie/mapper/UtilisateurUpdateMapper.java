package tech.steve.essaie.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import tech.steve.essaie.dto.utilisateur.UtilisateurUpdateDto;
import tech.steve.essaie.model.Utilisateur;

@Mapper(componentModel = "spring")
public interface UtilisateurUpdateMapper {
    @Mapping(target = "fermeId", source = "ferme.id")
    UtilisateurUpdateDto toDto(Utilisateur utilisateur);
    Utilisateur toEntity(UtilisateurUpdateDto dto);
}
