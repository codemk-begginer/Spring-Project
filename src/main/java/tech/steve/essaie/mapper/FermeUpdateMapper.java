package tech.steve.essaie.mapper;

import org.mapstruct.Mapper;
import tech.steve.essaie.dto.ferme.FermeUpdateDto;
import tech.steve.essaie.model.Ferme;

@Mapper(componentModel = "spring")
public interface FermeUpdateMapper {
    FermeUpdateDto toDto(Ferme ferme);
    Ferme toEntity(FermeUpdateDto dto);
}
