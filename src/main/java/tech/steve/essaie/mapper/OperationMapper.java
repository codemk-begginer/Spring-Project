package tech.steve.essaie.mapper;

import tech.steve.essaie.dto.OperationDto;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(target = "fermeId", source = "ferme.id")
    @Mapping(target = "animalId", source = "animal.id")
    OperationDto toDto(Operation operation);

    @Mapping(target = "ferme.id", source = "fermeId")
    @Mapping(target = "animal", source = "animalId", qualifiedByName = "mapAnimalFromId")
    Operation toEntity(OperationDto dto);

    @Named("mapAnimalFromId")
    static Animal mapAnimalFromId(UUID id) {
        if (id == null) return null;
        Animal animal = new Animal();
        animal.setId(id);
        return animal;
    }
}
