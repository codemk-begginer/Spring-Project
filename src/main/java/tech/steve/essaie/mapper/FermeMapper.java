package tech.steve.essaie.mapper;



import tech.steve.essaie.dto.ferme.FermeDto;
import tech.steve.essaie.model.Ferme;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // ✅ minuscules
public interface FermeMapper {
    FermeDto toDto(Ferme ferme);
    Ferme toEntity(FermeDto dto);
}

