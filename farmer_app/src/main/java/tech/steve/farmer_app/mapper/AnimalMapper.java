package tech.steve.farmer_app.mapper;

import com.porc.dto.AnimalDto;
import com.porc.model.Animal;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(source = "mere.id", target = "mereId")
    @Mapping(source = "pere.id", target = "pereId")
    AnimalDto toDto(Animal entity);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "qrCodeUrl", ignore = true)
    @Mapping(target = "mere", ignore = true)
    @Mapping(target = "pere", ignore = true)
    Animal toEntity(AnimalDto dto);
}

