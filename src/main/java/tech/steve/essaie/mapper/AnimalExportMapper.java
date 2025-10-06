package tech.steve.essaie.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.steve.essaie.dto.animal.AnimalDto;
import tech.steve.essaie.dto.animal.AnimalExportDto;
import tech.steve.essaie.model.Animal;

@Mapper(componentModel = "spring")
public interface AnimalExportMapper {
    @Mapping(source = "truie.id", target = "truie")
    @Mapping(source = "verrat.id", target = "verrat")
    AnimalExportDto toDto(Animal entity);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "qrCodeUrl", ignore = true)
    @Mapping(target = "truie", ignore = true)
    @Mapping(target = "verrat", ignore = true)
    Animal toEntity(AnimalExportDto dto);
}
