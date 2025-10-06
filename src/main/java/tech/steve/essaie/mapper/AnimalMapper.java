package tech.steve.essaie.mapper;

import tech.steve.essaie.dto.animal.AnimalDto;
import tech.steve.essaie.model.Animal;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(source = "truie.id", target = "truie")
    @Mapping(source = "verrat.id", target = "verrat")
    AnimalDto toDto(Animal entity);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "qrCodeUrl", ignore = true)
    @Mapping(target = "truie", ignore = true)
    @Mapping(target = "verrat", ignore = true)
    Animal toEntity(AnimalDto dto);


}

