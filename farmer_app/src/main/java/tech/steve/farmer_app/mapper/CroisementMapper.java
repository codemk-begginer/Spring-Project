package tech.steve.farmer_app.mapper;

import com.porc.dto.CroisementDto;
import com.porc.model.Animal;
import com.porc.model.Croisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CroisementMapper {

    @Mapping(target = "mere", source = "mereId", qualifiedByName = "mapToAnimal")
    @Mapping(target = "pere", source = "pereId", qualifiedByName = "mapToAnimal")
    Croisement toEntity(CroisementDto dto);

    @Mapping(source = "mere.id", target = "mereId")
    @Mapping(source = "pere.id", target = "pereId")
    CroisementDto toDto(Croisement entity);

    @Named("mapToAnimal")
    static Animal mapToAnimal(Long id) {
        if (id == null) return null;
        Animal a = new Animal();
        a.setId(id);
        return a;
    }
}



