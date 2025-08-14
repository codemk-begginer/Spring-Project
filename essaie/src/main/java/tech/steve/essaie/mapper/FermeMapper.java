package com.porc.mapper;

import com.porc.dto.FermeDto;
import com.porc.model.Ferme;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FermeMapper {
    FermeDto toDto(Ferme ferme);
    Ferme toEntity(FermeDto dto);
}
