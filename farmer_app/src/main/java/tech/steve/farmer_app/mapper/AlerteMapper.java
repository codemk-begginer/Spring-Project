package tech.steve.farmer_app.mapper;

import com.porc.dto.AlerteDto;
import com.porc.model.Alerte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AlerteMapper {

    @Mappings({
            @Mapping(source = "animal.id", target = "animalId"),
            @Mapping(source = "ferme.id", target = "fermeId")
    })
    AlerteDto toDto(Alerte alerte);

    @Mappings({
            @Mapping(source = "animalId", target = "animal.id"),
            @Mapping(source = "fermeId", target = "ferme.id")
    })
    Alerte toEntity(AlerteDto dto);
}
