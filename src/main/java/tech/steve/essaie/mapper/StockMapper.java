package tech.steve.essaie.mapper;

import org.mapstruct.Mapping;
import tech.steve.essaie.dto.StockDto;
import tech.steve.essaie.model.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {
    @Mapping(target = "Horodatage", source = "horodatage")
    StockDto toDto(Stock stock);
    @Mapping(target = "ferme.id", source = "fermeId")
    Stock toEntity(StockDto dto);
    List<StockDto> toDtoList(List<Stock> stocks);
}
