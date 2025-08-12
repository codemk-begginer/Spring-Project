package tech.steve.farmer_app.mapper;

import com.porc.dto.NaissanceDto;
import com.porc.model.Croisement;
import com.porc.model.Naissance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NaissanceMapper {

    @Mapping(source = "croisement.id", target = "croisementId")
    NaissanceDto toDto(Naissance entity);

    @Mapping(source = "croisementId", target = "croisement")
    Naissance toEntity(NaissanceDto dto);

    default Croisement map(Long id) {
        if (id == null) return null;
        Croisement c = new Croisement();
        c.setId(id);
        return c;
    }

    default Long map(Croisement croisement) {
        return croisement != null ? croisement.getId() : null;
    }
}


