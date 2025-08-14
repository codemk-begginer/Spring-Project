package com.porc.mapper;

import com.porc.dto.UtilisateurDto;
import com.porc.model.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    UtilisateurDto toDto(Utilisateur utilisateur);
    Utilisateur toEntity(UtilisateurDto dto);
}