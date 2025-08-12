package tech.steve.farmer_app.mapper;

import com.porc.dto.OperationDto;
import com.porc.model.Animal;
import com.porc.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(target = "animalId", source = "animal.id")
    OperationDto toDto(Operation operation);

    @Mapping(target = "animal", source = "animalId", qualifiedByName = "mapAnimalFromId")
    Operation toEntity(OperationDto dto);

    @Named("mapAnimalFromId")
    static Animal mapAnimalFromId(Long id) {
        if (id == null) return null;
        Animal animal = new Animal();
        animal.setId(id);
        return animal;
    }
}
