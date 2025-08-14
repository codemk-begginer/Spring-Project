package tech.steve.essaie.mapper;

import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.model.Animal;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(source = "mere.id", target = "mereId")
    @Mapping(source = "pere.id", target = "pereId")
    AnimalDto toDto(Animal entity);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = false)
    @Mapping(target = "code")
    @Mapping(target = "qrCodeUrl")
    @Mapping(target = "mere",ignore = true)
    @Mapping(target = "pere",ignore = true)
    Animal toEntity(AnimalDto dto);

    //void updateEntityFromDto(AnimalDto dto, Animal animal);
}

