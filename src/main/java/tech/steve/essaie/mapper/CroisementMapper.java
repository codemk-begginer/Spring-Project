package tech.steve.essaie.mapper;

import tech.steve.essaie.dto.CroisementDto;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Croisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CroisementMapper {

    @Mapping(target = "ferme.id", source = "fermeId")
    @Mapping(target = "truie", source = "truieId", qualifiedByName = "mapToAnimal")
    @Mapping(target = "verrat", source = "verratId", qualifiedByName = "mapToAnimal")
    Croisement toEntity(CroisementDto dto);

    @Mapping(target = "fermeId", source = "ferme.id")
    @Mapping(source = "truie.id", target = "truieId")
    @Mapping(source = "verrat.id", target = "verratId")
    CroisementDto toDto(Croisement entity);

    @Named("mapToAnimal")
    static Animal mapToAnimal(UUID id) {
        if (id == null) return null;
        Animal a = new Animal();
        a.setId(id);
        return a;
    }
}



