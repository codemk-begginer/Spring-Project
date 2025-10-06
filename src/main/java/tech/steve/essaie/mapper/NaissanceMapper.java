package tech.steve.essaie.mapper;

import tech.steve.essaie.dto.NaissanceDto;
import tech.steve.essaie.model.Croisement;
import tech.steve.essaie.model.Naissance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NaissanceMapper {

    @Mapping(target = "fermeId", source = "ferme.id")
    @Mapping(source = "croisement.id", target = "croisementId")
    NaissanceDto toDto(Naissance entity);

    @Mapping(target = "ferme.id", source = "fermeId")
    @Mapping(source = "croisementId", target = "croisement")
    Naissance toEntity(NaissanceDto dto);

    default Croisement map(UUID id) {
        if (id == null) return null;
        Croisement c = new Croisement();
        c.setId(id);
        return c;
    }

    default UUID map(Croisement croisement) {
        return croisement != null ? croisement.getId() : null;
    }
}


