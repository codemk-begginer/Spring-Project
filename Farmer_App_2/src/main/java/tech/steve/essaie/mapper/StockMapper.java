package tech.steve.essaie.mapper;

import tech.steve.essaie.dto.StockDto;
import tech.steve.essaie.model.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDto toDto(Stock stock);
    Stock toEntity(StockDto dto);
    List<StockDto> toDtoList(List<Stock> stocks);
}
